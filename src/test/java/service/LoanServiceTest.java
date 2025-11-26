package service;

import domain.Book;
import domain.User;
import org.junit.jupiter.api.Test;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoanServiceTest {

    // ==========================
    //   Mock Book Repo
    // ==========================
    private JsonBookRepository mockBookRepo() {
        return new JsonBookRepository() {

            private List<Book> books = new ArrayList<>();

            @Override
            public void save(Book book) {
                books.add(book);
            }

            @Override
            public List<Book> findAll() {
                return books;
            }

            @Override
            public Book findByIsbn(String isbn) {
                return books.stream()
                        .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                        .findFirst().orElse(null);
            }

            @Override
            public void update() {}
        };
    }

    // ==========================
    //   Mock User Repo
    // ==========================
    private JsonUserRepository mockUserRepo(List<User> initial) {
        return new JsonUserRepository() {

            private List<User> users = new ArrayList<>(initial);

            @Override
            public User findUser(String username) {
                return users.stream()
                        .filter(u -> u.getUsername().equalsIgnoreCase(username))
                        .findFirst().orElse(null);
            }

            @Override
            public void save(User user) {
                users.add(user);
            }

            @Override
            public List<User> findAll() {
                return users;
            }

            @Override
            public void update() {}
        };
    }

    // ==========================
    //   BORROW TESTS
    // ==========================

    @Test
    void testBorrowBookSuccess() {
        JsonBookRepository bookRepo = mockBookRepo();
        JsonUserRepository userRepo =
                mockUserRepo(List.of(new User("dana", "1234", "d@mail.com", 0)));

        Book b = new Book("Java", "A", "111");
        bookRepo.save(b);

        LoanService service = new LoanService(bookRepo, userRepo);

        LocalDate today = LocalDate.of(2025, 1, 1);
        String msg = service.borrowBook("dana", "111", today);

        assertTrue(msg.startsWith("Book borrowed successfully"));
        assertTrue(b.isBorrowed());
        assertEquals(today.plusDays(28), b.getDueDate());
    }

    @Test
    void testBorrowEmptyInputs() {
        LoanService service = new LoanService(mockBookRepo(), mockUserRepo(new ArrayList<>()));

        assertEquals("Username and ISBN cannot be empty!",
                service.borrowBook("", "111", LocalDate.now()));

        assertEquals("Username and ISBN cannot be empty!",
                service.borrowBook("dana", "", LocalDate.now()));
    }

    @Test
    void testBorrowUserNotFound() {
        JsonBookRepository bookRepo = mockBookRepo();
        bookRepo.save(new Book("Java", "A", "111"));

        LoanService service = new LoanService(bookRepo, mockUserRepo(new ArrayList<>()));

        assertEquals("User not found!", service.borrowBook("dana", "111", LocalDate.now()));
    }

    @Test
    void testBorrowUserHasUnpaidFines() {
        JsonBookRepository bookRepo = mockBookRepo();
        bookRepo.save(new Book("Java", "A", "111"));

        JsonUserRepository userRepo =
                mockUserRepo(List.of(new User("dana", "1234", "d@mail", 50)));

        LoanService service = new LoanService(bookRepo, userRepo);

        assertEquals("User has unpaid fines and cannot borrow books.",
                service.borrowBook("dana", "111", LocalDate.now()));
    }

    @Test
    void testBorrowBookNotFound() {
        JsonUserRepository userRepo =
                mockUserRepo(List.of(new User("dana", "1234", "d@mail", 0)));

        LoanService service = new LoanService(mockBookRepo(), userRepo);

        assertEquals("Book not found!", service.borrowBook("dana", "999", LocalDate.now()));
    }

    @Test
    void testBorrowBookAlreadyBorrowed() {
        JsonBookRepository bookRepo = mockBookRepo();
        Book b = new Book("Java", "A", "111");
        b.borrow("other", LocalDate.now());
        bookRepo.save(b);

        JsonUserRepository userRepo =
                mockUserRepo(List.of(new User("dana", "1234", "d@mail", 0)));

        LoanService service = new LoanService(bookRepo, userRepo);

        assertEquals("Book is already borrowed!", service.borrowBook("dana", "111", LocalDate.now()));
    }

    // ==========================
    //   OVERDUE TESTS
    // ==========================

    @Test
    void testBookNotOverdueWhenNotBorrowed() {
        Book b = new Book("Java", "A", "111");

        LoanService s = new LoanService(mockBookRepo(), mockUserRepo(new ArrayList<>()));

        assertFalse(s.isBookOverdue(b, LocalDate.now()));
    }

    @Test
    void testBookOverdueAfterDueDate() {
        Book b = new Book("Java", "A", "111");
        b.borrow("dana", LocalDate.of(2025, 1, 1));

        LoanService s = new LoanService(mockBookRepo(), mockUserRepo(new ArrayList<>()));

        assertTrue(s.isBookOverdue(b, LocalDate.of(2025, 1, 2)));
    }

    // ==========================
    //   PAY FINE TESTS
    // ==========================

    @Test
    void testPayFineFull() {
        User u = new User("dana", "1234", "d@mail", 30);

        JsonUserRepository userRepo = mockUserRepo(List.of(u));
        LoanService s = new LoanService(mockBookRepo(), userRepo);

        assertEquals("Fine fully paid. Remaining fine: 0 NIS.",
                s.payFine("dana", 30));

        assertEquals(0, u.getFineBalance());
    }

    @Test
    void testPayFinePartial() {
        User u = new User("dana", "1234", "d@mail", 50);

        JsonUserRepository userRepo = mockUserRepo(List.of(u));
        LoanService s = new LoanService(mockBookRepo(), userRepo);

        assertEquals("Partial payment accepted. Remaining fine: 30 NIS.",
                s.payFine("dana", 20));

        assertEquals(30, u.getFineBalance());
    }

    @Test
    void testPayFineNoFines() {
        User u = new User("dana", "1234", "d@mail", 0);

        JsonUserRepository userRepo = mockUserRepo(List.of(u));
        LoanService s = new LoanService(mockBookRepo(), userRepo);

        assertEquals("User has no fines to pay.", s.payFine("dana", 10));
    }

    @Test
    void testPayFineUserNotFound() {
        LoanService s = new LoanService(mockBookRepo(),
                mockUserRepo(new ArrayList<>()));

        assertEquals("User not found!", s.payFine("unknown", 10));
    }

    @Test
    void testPayFineInvalidAmount() {
        User u = new User("dana", "1234", "d@mail", 20);

        LoanService s = new LoanService(mockBookRepo(),
                mockUserRepo(List.of(u)));

        assertEquals("Payment amount must be positive!", s.payFine("dana", 0));
    }
}
