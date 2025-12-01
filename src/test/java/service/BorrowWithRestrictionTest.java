package service;

import domain.Book;
import domain.User;
import org.junit.jupiter.api.Test;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BorrowWithRestrictionTest {

    private JsonUserRepository mockUserRepo(List<User> users) {
        return new JsonUserRepository(users);
    }

    private JsonBookRepository mockBookRepo(List<Book> books) {
        return new JsonBookRepository(books);
    }

    @Test
    void borrowBlockedWhenUserHasOverdueBooks() {
        User u = new User("alice", "pw", "a@mail", 0);
        Book b1 = new Book("T", "A", "111");
        b1.borrow("alice", LocalDate.now().minusDays(30)); // overdue
        Book b2 = new Book("Other","B","222");
        JsonUserRepository ur = mockUserRepo(List.of(u));
        JsonBookRepository br = mockBookRepo(List.of(b1,b2));
        AdminService admin = new AdminService(new presentation.JsonAdminRepository(), new domain.Session());
        LoanService loan = new LoanService(br, ur);
        AdvancedService adv = new AdvancedService(ur, br, admin, loan);

        String res = adv.borrowWithRestrictions("alice", "222");
        assertEquals("User has overdue books and cannot borrow books.", res);
    }

    @Test
    void unregisterBlockedWhenActiveLoansOrFines() {
        User u = new User("bob","pw","b@mail",0);
        Book b1 = new Book("X","Y","333");
        b1.borrow("bob", LocalDate.now().plusDays(5)); // active loan
        JsonUserRepository ur = mockUserRepo(List.of(u));
        JsonBookRepository br = mockBookRepo(List.of(b1));
        AdminService admin = new AdminService(new presentation.JsonAdminRepository(), new domain.Session());
        LoanService loan = new LoanService(br, ur);
        AdvancedService adv = new AdvancedService(ur, br, admin, loan);

        // admin not logged -> cannot unregister
        String res = adv.unregisterUser("bob");
        assertEquals("Only admins can unregister users.", res);

        // simulate admin login
        admin.login("admin","admin"); // assuming default admin exists in repo
        res = adv.unregisterUser("bob");
        assertEquals("User has active loans and cannot be unregistered.", res);
    }
}

