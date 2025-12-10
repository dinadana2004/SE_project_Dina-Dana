package service;

import domain.Book;
import domain.User;
import org.junit.jupiter.api.Test;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BorrowUnderRestrictiondsTest {

   
    private JsonUserRepository mockUserRepo() {
        return new FakeUserRepository();
    }

    private JsonBookRepository mockBookRepo() {
        return new FakeBookRepository();
    }

    private LoanService mockLoanService(JsonBookRepository br, JsonUserRepository ur) {
        return new LoanService(br, ur);
    }

    @Test
    void testBorrowSuccess() {
        JsonUserRepository ur = mockUserRepo();
        JsonBookRepository br = mockBookRepo();

        ur.save(new User("alice", "pw", "a@mail", 0));
        br.save(new Book("Java", "Dana", "111"));

        LoanService loan = mockLoanService(br, ur);
        AdminService admin = new AdminService(null, new domain.Session());

        BorrowUnderRestrictionds service =
                new BorrowUnderRestrictionds(ur, br, admin, loan);

        String msg = service.borrowRestrictions("alice", "111");
        // LoanService يرجّع "Book borrowed successfully! Due date: …"
        assertTrue(msg.startsWith("Book borrowed successfully"),
                "Expected success borrow message, but was: " + msg);
    }

    @Test
    void testBorrowInputValidations() {
        JsonUserRepository ur = mockUserRepo();
        JsonBookRepository br = mockBookRepo();
        LoanService loan = mockLoanService(br, ur);
        AdminService admin = new AdminService(null, new domain.Session());

        BorrowUnderRestrictionds service =
                new BorrowUnderRestrictionds(ur, br, admin, loan);

        // 1) username empty
        assertEquals("username shouldn't be empty",
                service.borrowRestrictions("", "111"));

        // 2) isbn empty
        assertEquals("isbn shouldn't be empty",
                service.borrowRestrictions("alice", ""));

        // 3) user does not exist
        assertEquals("this user does not exist",
                service.borrowRestrictions("bob", "111"));
    }

    @Test
    void testBorrowRejectedDueToUnpaidFines() {
        JsonUserRepository ur = mockUserRepo();
        JsonBookRepository br = mockBookRepo();

        User u = new User("alice", "pw", "mail", 50); // عنده غرامات
        ur.save(u);
        br.save(new Book("Java", "Dana", "111"));

        LoanService loan = mockLoanService(br, ur);
        AdminService admin = new AdminService(null, new domain.Session());

        BorrowUnderRestrictionds service =
                new BorrowUnderRestrictionds(ur, br, admin, loan);

        assertEquals(
                "this borrowing operation has rejected because this user has unpaid fines,he/she should pay the fines first in order to borrow a book",
                service.borrowRestrictions("alice", "111"));
    }

    @Test
    void testBorrowRejectedDueToOverdueBooks() {
        JsonUserRepository ur = mockUserRepo();
        JsonBookRepository br = mockBookRepo();

        User u = new User("alice", "pw", "mail", 0);
        ur.save(u);

        Book overdue = new Book("Old", "X", "222");
        overdue.borrow("alice", LocalDate.now().minusDays(30));
        br.save(overdue);

        Book normal = new Book("New", "Y", "111");
        br.save(normal);

        LoanService loan = mockLoanService(br, ur);
        AdminService admin = new AdminService(null, new domain.Session());

        BorrowUnderRestrictionds service =
                new BorrowUnderRestrictionds(ur, br, admin, loan);

        assertEquals("User has overdue books and cannot borrow books.",
                service.borrowRestrictions("alice", "111"));
    }
}