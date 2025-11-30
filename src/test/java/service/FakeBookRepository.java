package service;

import domain.Book;
import java.util.ArrayList;
import java.util.List;

public class FakeBookRepository extends presentation.JsonBookRepository {

    public List<Book> booksList = new ArrayList<>();

    @Override
    public List<Book> findAll() {
        return booksList;
    }

    @Override
    public void save(Book book) {
        booksList.add(book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return booksList.stream()
                .filter(b -> b.getIsbn().equalsIgnoreCase(isbn))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update() {
        // do nothing (no JSON)
    }
}
