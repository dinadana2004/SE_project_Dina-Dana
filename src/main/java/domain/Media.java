package domain;

import java.time.LocalDate;

/**
 * Base abstract class for media items (e.g., books, CDs).
 * Provides common borrowing state and behavior.
 *
 * @author Dana
 * @version 1.0
 */
public abstract class Media {

    /** Media title. */
    protected String title;

    /** Whether the media item is currently borrowed. */
    protected boolean borrowed;

    /** Username of the borrower, if borrowed. */
    protected String borrowedByUser;

    /** Due date for returning this media item. */
    protected LocalDate dueDate;

    /**
     * Creates a media item with the given title.
     *
     * @param title the title of the media item
     */
    public Media(String title) {
        this.title = title;
        this.borrowed = false;
    }

    /**
     * @return the title of the media item
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return true if the media is currently borrowed
     */
    public boolean isBorrowed() {
        return borrowed;
    }

    /**
     * @return the username of the borrower, or null if not borrowed
     */
    public String getBorrowedByUser() {
        return borrowedByUser;
    }

    /**
     * @return the due date for returning this media, or null if not borrowed
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Marks this media item as borrowed by the given user until the given due date.
     *
     * @param username the username of the borrower
     * @param dueDate  due date for return
     */
    public void borrow(String username, LocalDate dueDate) {
        this.borrowed = true;
        this.borrowedByUser = username;
        this.dueDate = dueDate;
    }

    /**
     * Returns this media item, clearing its borrowing information.
     */
    public void returnMedia() {
        this.borrowed = false;
        this.borrowedByUser = null;
        this.dueDate = null;
    }

    /**
     * Returns how many days this media type can be borrowed.
     *
     * @return number of allowed borrowing days
     */
    public abstract int getBorrowDays();

    /**
     * Returns a short name of the media type (e.g., BOOK, CD).
     *
     * @return the media type name
     */
    public abstract String getMediaType();
}