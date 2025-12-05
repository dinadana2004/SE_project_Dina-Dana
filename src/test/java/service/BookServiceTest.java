package service;

import domain.Book;
import org.junit.jupiter.api.Test;
import presentation.JsonBookRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private JsonBookRepository mockBookRepo() {
        return new JsonBookRepository() {

            private List<Book> books = new ArrayList<>();

            @Override
            public void save(Book book) {
                books.add(book);
            }

            @Override
            public List<Book> findAll() {
                return books;
            }

            @Override
            public Book findByIsbn(String isbn) {
                return books.stream()
                        .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                        .findFirst().orElse(null);
            }

            @Override
            public void update() {}
        };
    }

    @Test
    void testAddBookSuccess() {
        JsonBookRepository repo = mockBookRepo();
        BookService service = new BookService(repo);

        Book b = new Book("Java", "Dana", "111");
        assertEquals("Book added successfully!", service.addBook(b));
    }

    @Test
    void testAddBookNull() {
        JsonBookRepository repo = mockBookRepo();
        BookService service = new BookService(repo);

        assertEquals("Book cannot be null!", service.addBook(null));
    }

    @Test
    void testAddBookEmptyFields() {
        JsonBookRepository repo = mockBookRepo();
        BookService service = new BookService(repo);

        Book b = new Book("", "A", "999");
        assertEquals("Book title, author, and ISBN cannot be empty!", service.addBook(b));
    }

    @Test
    void testAddBookDuplicateISBN() {
        JsonBookRepository repo = mockBookRepo();
        repo.save(new Book("Old", "X", "999"));

        BookService service = new BookService(repo);
        Book b = new Book("New", "Y", "999");

        assertEquals("A book with this ISBN already exists!", service.addBook(b));
    }

    @Test
    void testSearchEmptyKeyword() {
        JsonBookRepository repo = mockBookRepo();
        repo.save(new Book("Java", "Dana", "111"));
        repo.save(new Book("Python", "Ali", "222"));

        BookService service = new BookService(repo);

        assertEquals(2, service.search("").size());
    }

    @Test
    void testSearchMatch() {
        JsonBookRepository repo = mockBookRepo();
        repo.save(new Book("Advanced Java", "Dana", "111"));

        BookService service = new BookService(repo);

        assertEquals(1, service.search("java").size());
    }

    @Test
    void testSearchNoMatch() {
        JsonBookRepository repo = mockBookRepo();
        repo.save(new Book("Advanced Java", "Dana", "111"));

        BookService service = new BookService(repo);

        assertEquals(0, service.search("banana").size());
    }
}
