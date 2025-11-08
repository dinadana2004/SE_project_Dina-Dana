import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import domain.Book;
import service.BookService;

public class searchBook_testing {

    @Test
    public void search_via_title() {
        BookService service = new BookService();
        Book book = new Book("Science", "John", "100");
        service.add_NewBook(book);

        List<Book> result = service.search_book_title("Science");
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getAuthor());
    }

    @Test
    public void search_via_author() {
        BookService service = new BookService();
        service.add_NewBook(new Book("Science", "John", "100"));
        service.add_NewBook(new Book("Math", "John", "200"));

        List<Book> result = service.search_book_author("John");
        assertEquals(2, result.size()); // two books by John
    }

    @Test
    public void search_via_isbn() {
        BookService service = new BookService();
        service.add_NewBook(new Book("Science", "John", "100"));

        Book result = service.search_book_isbn("100");
        assertEquals("Science", result.getTitle());
    }

    @Test
    public void search_via_nullValue() {
        BookService service = new BookService();
        service.add_NewBook(new Book("Science", "John", "100"));

        List<Book> result = service.search_book_title(null);
        assertTrue(result.isEmpty()); // you could also return null if you prefer
    }
}

