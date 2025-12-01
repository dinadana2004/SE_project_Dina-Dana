package service;

import domain.Book;
import presentation.JsonBookRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling book-related operations such as
 * adding books and searching for books in the library system.
 *
 * <p>This service communicates with the {@link JsonBookRepository}
 * to store and retrieve book data.</p>
 *
 * @version 1.0
 * @author Dana
 */
public class BookService {

    /** Repository that manages persistent storage of books. */
    private final JsonBookRepository repo;

    /**
     * Constructs a BookService with the given book repository.
     *
     * @param repo the repository used to save and retrieve books
     */
    public BookService(JsonBookRepository repo) {
        this.repo = repo;
    }

    /**
     * Adds a new book to the library after validating its data.
     *
     * @param b the {@link Book} object to be added
     * @return a status message describing whether the operation succeeded or failed
     */
    public String addBook(Book b) {

        if (b == null)
            return "Book cannot be null!";

        if (b.getTitle().trim().isEmpty() ||
            b.getAuthor().trim().isEmpty() ||
            b.getIsbn().trim().isEmpty()) {
            return "Book title, author, and ISBN cannot be empty!";
        }

        if (repo.findByIsbn(b.getIsbn()) != null)
            return "A book with this ISBN already exists!";

        repo.save(b);
        return "Book added successfully!";
    }

    /**
     * Searches for books whose title, author, or ISBN match the specified keyword.
     *
     * @param keyword the search term (case-insensitive); if empty, all books are returned
     * @return a list of matching {@link Book} objects
     */
    public List<Book> search(String keyword) {

        if (keyword == null || keyword.trim().isEmpty())
            return repo.findAll();

        String q = keyword.toLowerCase();

        return repo.findAll().stream()
                .filter(b ->
                        b.getTitle().toLowerCase().contains(q) ||
                        b.getAuthor().toLowerCase().contains(q) ||
                        b.getIsbn().toLowerCase().contains(q))
                .collect(Collectors.toList());
    }
}
