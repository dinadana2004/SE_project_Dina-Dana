package presentation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonBookRepository {

    private static final String FILE = "src/main/resources/books.json";
    private List<Book> books;

    public JsonBookRepository() {
        try {
            FileReader fr = new FileReader(FILE);
            books = new Gson().fromJson(fr,
                    new TypeToken<List<Book>>(){}.getType());
            if (books == null) books = new ArrayList<>();
        } catch (Exception e) {
            books = new ArrayList<>();
        }
    }

    public void save(Book book) {
        books.add(book);
        writeFile();
    }

    public List<Book> findAll() { return books; }

    private void writeFile() {
        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(new Gson().toJson(books));
        } catch (IOException e) {
            throw new RuntimeException("Error writing books.json");
        }
    }
}
