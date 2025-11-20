package service;

import domain.Book;
import presentation.JsonBookRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private final JsonBookRepository repo;

    public BookService(JsonBookRepository repo) {
        this.repo = repo;
    }

    /**
     * Adds a book with all validation checks.
     */
    public String addBook(Book book) {

        // Case 1: null object
        if (book == null) {
            return "Book cannot be null!";
        }

        // Case 2: empty fields
        if (book.getTitle().trim().isEmpty()
                || book.getAuthor().trim().isEmpty()
                || book.getIsbn().trim().isEmpty()) {
            return "Book title, author, and ISBN cannot be empty!";
        }

        // Case 3: duplicate ISBN
        boolean exists = repo.findAll().stream()
                .anyMatch(b -> b.getIsbn().equalsIgnoreCase(book.getIsbn()));

        if (exists) {
            return "A book with this ISBN already exists!";
        }

        // Case 4: success
        repo.save(book);
        return "Book added successfully!";
    }


    /**
     * Searches for a book safely.
     */
    public List<Book> search(String keyword) {

        // Case 1: null or empty -> return all
        if (keyword == null || keyword.trim().isEmpty()) {
            return repo.findAll();
        }

        String query = keyword.toLowerCase();

        return repo.findAll().stream()
                .filter(b ->
                        b.getTitle().toLowerCase().contains(query)
                                || b.getAuthor().toLowerCase().contains(query)
                                || b.getIsbn().toLowerCase().contains(query)
                )
                .collect(Collectors.toList());
    }
}

