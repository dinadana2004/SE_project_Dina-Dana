package service;

import domain.Book;
import presentation.JsonBookRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    // Fake Book Repository (in-memory)
    private JsonBookRepository mockBookRepo() {
        return new JsonBookRepository() {

            private final List<Book> books = new ArrayList<>();

            @Override
            public void save(Book book) {
                books.add(book);
            }

            @Override
            public List<Book> findAll() {
                return books;
            }
        };
    }

    @Test
    void testAddBookSuccess() {
        var repo = mockBookRepo();
        var service = new BookService(repo);

        Book b = new Book("Java", "Dana", "111");
        assertEquals("Book added successfully!", service.addBook(b));
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void testAddBookNull() {
        var repo = mockBookRepo();
        var service = new BookService(repo);

        assertEquals("Book cannot be null!", service.addBook(null));
    }

    @Test
    void testAddBookEmptyFields() {
        var repo = mockBookRepo();
        var service = new BookService(repo);

        Book b = new Book("", "Dana", "222");
        assertEquals("Book title, author, and ISBN cannot be empty!", service.addBook(b));
    }

    @Test
    void testAddBookDuplicateISBN() {
        var repo = mockBookRepo();
        repo.save(new Book("Old Book", "A", "999")); // existing book

        var service = new BookService(repo);
        Book newBook = new Book("New Book", "B", "999");

        assertEquals("A book with this ISBN already exists!", service.addBook(newBook));
        assertEquals(1, repo.findAll().size()); // still 1 book only
    }

    @Test
    void testSearchReturnsAllWhenEmptyKeyword() {
        var repo = mockBookRepo();
        repo.save(new Book("Java", "Dana", "111"));
        repo.save(new Book("Python", "Ali", "222"));

        var service = new BookService(repo);

        List<Book> result = service.search("");
        assertEquals(2, result.size());
    }

    @Test
    void testSearchByTitle() {
        var repo = mockBookRepo();
        repo.save(new Book("Advanced Java", "Dana", "111"));

        var service = new BookService(repo);

        List<Book> result = service.search("java");
        assertEquals(1, result.size());
    }

    @Test
    void testSearchByISBN() {
        var repo = mockBookRepo();
        repo.save(new Book("Math", "Dana", "555"));

        var service = new BookService(repo);

        List<Book> result = service.search("555");
        assertEquals(1, result.size());
    }
}
