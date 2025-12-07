package service;

import domain.Book;
import domain.User;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OverDueBookTest {

    /**
     * LoanService fake to return a predefined list of overdue books.
     */
    private static class FakeLoanService extends LoanService {
        private final List<Book> overdueBooks;

        public FakeLoanService(List<Book> overdueBooks) {
            super(null, null); // We won't use repos here
            this.overdueBooks = overdueBooks;
        }

        @Override
        public List<Book> getOverdueBooks(LocalDate today) {
            return overdueBooks;
        }
    }

    @Test
    void testNoOverdueBooks() {
        // TODAY is fixed:
        Clock fixedClock = Clock.fixed(
                Instant.parse("2025-01-01T00:00:00Z"),
                ZoneId.systemDefault()
        );

        User u = new User("dana", "pw", "dana@mail.com");

        // No overdue books at all:
        FakeLoanService fakeLoan = new FakeLoanService(List.of());

        OverDueBook service = new OverDueBook(fixedClock, fakeLoan);

        assertEquals(0, service.getOverDueCount(u));
    }

    @Test
    void testUserHasOneOverdueBook() {

        Clock fixedClock = Clock.fixed(
                Instant.parse("2025-01-01T00:00:00Z"),
                ZoneId.systemDefault()
        );

        User u = new User("dana", "pw", "dana@mail.com");

        // overdue book for dana
        Book overdue = new Book("Java", "James", "111");
        overdue.borrow("dana", LocalDate.of(2024, 12, 20)); // due date before 2025-01-01

        FakeLoanService fakeLoan = new FakeLoanService(List.of(overdue));

        OverDueBook service = new OverDueBook(fixedClock, fakeLoan);

        assertEquals(1, service.getOverDueCount(u));
    }


    @Test
    void testMixedOverdueDifferentUsers() {

        Clock fixedClock = Clock.fixed(
                Instant.parse("2025-01-01T00:00:00Z"),
                ZoneId.systemDefault()
        );

        User u = new User("dana", "pw", "dana@mail.com");

        Book overdue1 = new Book("Java", "James", "111");
        overdue1.borrow("dana", LocalDate.of(2024, 12, 15)); // overdue

        Book overdue2 = new Book("C++", "Bjarne", "222");
        overdue2.borrow("john", LocalDate.of(2024, 11, 30)); // overdue but NOT dana

        FakeLoanService fakeLoan = new FakeLoanService(List.of(overdue1, overdue2));

        OverDueBook service = new OverDueBook(fixedClock, fakeLoan);

        // Only overdue1 belongs to dana:
        assertEquals(1, service.getOverDueCount(u));
    }
}