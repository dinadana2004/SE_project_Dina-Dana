package domain;

import java.time.LocalDate;

/**
 * Represents a book in the library system.
 * <p>
 * A book contains basic bibliographic details such as title, author, and ISBN.
 * It may also have borrowing information if it is currently borrowed by a user.
 * </p>
 * 
 * <p>Borrowing details include:</p>
 * <ul>
 *     <li>whether the book is borrowed</li>
 *     <li>the username of the borrower</li>
 *     <li>the due date for return</li>
 * </ul>
 * 
 * @author Dana
 * @version 1.0
 */
public class Book {

    /** The title of the book. */
    private String title;

    /** The author of the book. */
    private String author;

    /** The ISBN identifier of the book. */
    private String isbn;

    /** Whether the book is currently borrowed. */
    private boolean borrowed;

    /** The username of the user who borrowed the book. */
    private String borrowedByUser;

    /** The due date by which the book must be returned. */
    private LocalDate dueDate;

    /**
     * Default constructor required for JSON deserialization.
     */
    public Book() {}

    /**
     * Creates a new Book object with the specified title, author, and ISBN.
     *
     * @param title  the title of the book
     * @param author the author of the book
     * @param isbn   the ISBN identifier
     */
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;

        this.borrowed = false;
        this.borrowedByUser = null;
        this.dueDate = null;
    }

    /** @return the title of the book */
    public String getTitle() { return title; }

    /** @return the author of the book */
    public String getAuthor() { return author; }

    /** @return the ISBN of the book */
    public String getIsbn() { return isbn; }

    /** @return true if the book is currently borrowed */
    public boolean isBorrowed() { return borrowed; }

    /** @return the username of the borrower if borrowed, otherwise null */
    public String getBorrowedByUser() { return borrowedByUser; }

    /** @return the due date if the book is borrowed, otherwise null */
    public LocalDate getDueDate() { return dueDate; }

    /**
     * Marks the book as borrowed by a specific user until a given due date.
     *
     * @param username the username of the borrower
     * @param dueDate  the date the book is due to be returned
     */
    public void borrow(String username, LocalDate dueDate) {
        this.borrowed = true;
        this.borrowedByUser = username;
        this.dueDate = dueDate;
    }

    /**
     * Returns the book, resetting its borrowing details.
     */
    public void returnBook() {
        this.borrowed = false;
        this.borrowedByUser = null;
        this.dueDate = null;
    }
}
