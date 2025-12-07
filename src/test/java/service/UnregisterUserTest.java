package service;

import domain.User;
import domain.Book;
import org.junit.jupiter.api.Test;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnregisterUserTest {

    // Fake User Repository to avoid modifying JSON files
    private JsonUserRepository mockUserRepo(List<User> initial) {
        return new JsonUserRepository() {

            private List<User> users = new ArrayList<>(initial);

            @Override
            public void save(User user) {
                users.add(user);
            }

            @Override
            public List<User> findAll() {
                return users;
            }

            @Override
            public User findUser(String username) {
                return users.stream()
                        .filter(u -> u.getUsername().equalsIgnoreCase(username))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public void update() {
                // No file update during tests
            }
        };
    }

    // Fake Book Repository
    private JsonBookRepository mockBookRepo(List<Book> initial) {
        return new JsonBookRepository() {

            private List<Book> books = new ArrayList<>(initial);

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
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public void update() {
                // Skip file update
            }
        };
    }

    // Fake admin service
    private AdminService mockAdminService(boolean loggedIn) {
        return new AdminService(null, new domain.Session()) {
            @Override
            public boolean isLoggedIn() {
                return loggedIn;
            }
        };
    }

    private LoanService mockLoanService() {
        return new LoanService(null, null);
    }

    // ------------------ TEST CASES ------------------ //

    @Test
    void testAdminNotLoggedIn() {
        JsonUserRepository userRepo = mockUserRepo(new ArrayList<>());
        JsonBookRepository bookRepo = mockBookRepo(new ArrayList<>());
        AdminService adminService = mockAdminService(false);

        UnregisterUser service =
                new UnregisterUser(userRepo, bookRepo, adminService, mockLoanService());

        assertEquals("Only admins can unregister users.",
                service.unregisterUser("john"));
    }

    @Test
    void testEmptyUsername() {
        JsonUserRepository userRepo = mockUserRepo(new ArrayList<>());
        JsonBookRepository bookRepo = mockBookRepo(new ArrayList<>());
        AdminService adminService = mockAdminService(true);

        UnregisterUser service =
                new UnregisterUser(userRepo, bookRepo, adminService, mockLoanService());

        assertEquals("username shouldn't be empty",
                service.unregisterUser("   "));
    }

    @Test
    void testUserDoesNotExist() {
        JsonUserRepository userRepo = mockUserRepo(new ArrayList<>());
        JsonBookRepository bookRepo = mockBookRepo(new ArrayList<>());
        AdminService adminService = mockAdminService(true);

        UnregisterUser service =
                new UnregisterUser(userRepo, bookRepo, adminService, mockLoanService());

        assertEquals("this user does not exist",
                service.unregisterUser("unknown"));
    }

    @Test
    void testUserHasUnpaidFines() {
        User u = new User("john", "pass", "john@mail.com", 10); // has fines

        JsonUserRepository userRepo = mockUserRepo(List.of(u));
        JsonBookRepository bookRepo = mockBookRepo(new ArrayList<>());
        AdminService adminService = mockAdminService(true);

        UnregisterUser service =
                new UnregisterUser(userRepo, bookRepo, adminService, mockLoanService());

        assertEquals("User has unpaid fines and cannot be unregistered.",
                service.unregisterUser("john"));
    }

    @Test
    void testUserHasActiveLoans() {
        User u = new User("john", "pass", "john@mail.com");

        Book borrowed = new Book("Java", "Dana", "111");
        // Borrow book properly
        borrowed.borrow("john", LocalDate.now().plusDays(7));

        JsonUserRepository userRepo = mockUserRepo(List.of(u));
        JsonBookRepository bookRepo = mockBookRepo(List.of(borrowed));
        AdminService adminService = mockAdminService(true);

        UnregisterUser service =
                new UnregisterUser(userRepo, bookRepo, adminService, mockLoanService());

        assertEquals("User has active loans and cannot be unregistered.",
                service.unregisterUser("john"));
    }

    @Test
    void testSuccessfulUnregister() {
        User u = new User("john", "pass", "john@mail.com");

        JsonUserRepository userRepo = mockUserRepo(new ArrayList<>(List.of(u)));
        JsonBookRepository bookRepo = mockBookRepo(new ArrayList<>());
        AdminService adminService = mockAdminService(true);

        UnregisterUser service =
                new UnregisterUser(userRepo, bookRepo, adminService, mockLoanService());

        assertEquals("User unregistered successfully.",
                service.unregisterUser("john"));

        assertNull(userRepo.findUser("john"));
    }
}