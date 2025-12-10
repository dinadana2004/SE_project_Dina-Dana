package presentation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Book;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing {@link Book} data stored in a JSON file.
 * It handles loading, saving, searching, and updating book records using Gson.
 *
 * <p>This class supports serialization of {@link LocalDate} through a custom adapter.</p>
 *
 * @author Dana
 * @version 1.0
 */
public class JsonBookRepository {

    /** The JSON file path where all book records are stored. */
    private static final String FILE = "src/main/resources/books.json";

    /**
     * A configured Gson instance that supports LocalDate serialization
     * and generates human-readable JSON output.
     */
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    /** Internal list containing all stored books. */
    private List<Book> books;

    /**
     * Constructs a JsonBookRepository and loads the list of books from the JSON file.
     * If the file is missing or unreadable, an empty list is created.
     */
    public JsonBookRepository() {
        try {
            FileReader fr = new FileReader(FILE);
            books = gson.fromJson(fr, new TypeToken<List<Book>>(){}.getType());
            if (books == null) books = new ArrayList<>();
        } catch (Exception e) {
            books = new ArrayList<>();
        }
    }

    /**
     * Saves a new book to the repository and writes changes to the JSON file.
     *
     * @param book the {@link Book} object to be saved
     */
    public void save(Book book) {
        books.add(book);
        writeFile();
    }

    /**
     * Retrieves all books stored in the repository.
     *
     * @return a list of {@link Book} objects
     */
    public List<Book> findAll() {
        return books;
    }

    /**
     * Searches for a book using its ISBN.
     *
     * @param isbn the book's ISBN (case-insensitive)
     * @return the matching {@link Book}, or {@code null} if not found
     */
    public Book findByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    /**
     * Forces an update to the JSON file by rewriting the entire book list.
     * Useful after modifying an existing book's details.
     */
    public void update() {
        writeFile();
    }

    /**
     * Writes the current list of books to the JSON file.
     *
     * @throws RuntimeException if writing to the file fails
     */
    private void writeFile() {
        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(gson.toJson(books));
        } catch (IOException e) {
            throw new RuntimeException("Error writing books.json");
        }
    }
}
