package domain;

/**
 * Represents a book as a media item with a fixed 28-day borrowing period.
 */
public class BookMedia extends Media {

    private String author;
    private String isbn;

    /**
     * Creates a book media item with the given details.
     *
     * @param title  book title
     * @param author book author
     * @param isbn   book ISBN
     */
    public BookMedia(String title, String author, String isbn) {
        super(title);
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public int getBorrowDays() {
        return 28; // US5.1 rule for books
    }

    @Override
    public String getMediaType() {
        return "BOOK";
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }
}