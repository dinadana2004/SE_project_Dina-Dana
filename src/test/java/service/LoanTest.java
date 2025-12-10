package service;
import domain.Book;
import domain.User;
import domain.Loan;



import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void testLoanConstructorAndGetters() {
        Book book = new Book("Java", "James", "111");
        User user = new User("dana", "123", "dana@mail.com");

        LocalDate borrowDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 10);

        Loan loan = new Loan(book, user, borrowDate, dueDate);

        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(borrowDate, loan.getBorrowDate());
        assertEquals(dueDate, loan.getDueDate());
    }

    @Test
    void testIsOverdueWhenNotOverdue() {
        Loan loan = new Loan(
                new Book("Java", "J", "111"),
                new User("dana", "123", "dana@mail.com"),
                LocalDate.now().minusDays(5),
                LocalDate.now().plusDays(3)
        );

        assertFalse(loan.isOverdue(LocalDate.now()));
    }

    @Test
    void testIsOverdueWhenExactlyDue() {
        LocalDate today = LocalDate.now();
        Loan loan = new Loan(
                new Book("Java", "J", "111"),
                new User("dana", "123", "dana@mail.com"),
                today.minusDays(5),
                today
        );

        assertFalse(loan.isOverdue(today));
    }

    @Test
    void testIsOverdueWhenOverdue() {
        Loan loan = new Loan(
                new Book("Java", "J", "111"),
                new User("dana", "123", "dana@mail.com"),
                LocalDate.now().minusDays(10),
                LocalDate.now().minusDays(1)
        );

        assertTrue(loan.isOverdue(LocalDate.now()));
    }
}