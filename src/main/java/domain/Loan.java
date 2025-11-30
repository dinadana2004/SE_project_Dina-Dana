package domain;

import java.time.LocalDate;

/**
 * Represents a loan transaction in the library system.
 * <p>
 * A loan connects a {@link Book} with the {@link User} who borrowed it,
 * along with the borrow date and the due date for returning the book.
 * </p>
 *
 * <p>This class is immutable: all fields are final and set only once
 * when the loan is created.</p>
 *
 * @version 1.0
 * @author Dana
 */
public class Loan {

    /** The book being borrowed. */
    private final Book book;

    /** The user who borrowed the book. */
    private final User user;

    /** The date when the book was borrowed. */
    private final LocalDate borrowDate;

    /** The date by which the book must be returned. */
    private final LocalDate dueDate;

    /**
     * Creates a new Loan record with the given book, user, borrow date, and due date.
     *
     * @param book        the borrowed book
     * @param user        the user who borrowed the book
     * @param borrowDate  the date the book was borrowed
     * @param dueDate     the date by which the book must be returned
     */
    public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    /** @return the borrowed book */
    public Book getBook() { return book; }

    /** @return the user who borrowed the book */
    public User getUser() { return user; }

    /** @return the date the book was borrowed */
    public LocalDate getBorrowDate() { return borrowDate; }

    /** @return the due date for returning the book */
    public LocalDate getDueDate() { return dueDate; }

    /**
     * Determines whether this loan is overdue based on the specified date.
     *
     * @param today the date to compare with the due date
     * @return {@code true} if today is after the due date, otherwise {@code false}
     */
    public boolean isOverdue(LocalDate today) {
        return today.isAfter(dueDate);
    }
}
