package domain;


/**
* Represents a book in the library.
*/
public class Book {
private final String title;
private final String author;
private final String isbn;
private boolean borrowed = false;


public Book(String title, String author, String isbn) {
this.title = title;
this.author = author;
this.isbn = isbn;
}


public String getTitle() { return title; }
public String getAuthor() { return author; }
public String getIsbn() { return isbn; }


public boolean isBorrowed() { return borrowed; }
public void setBorrowed(boolean borrowed) { this.borrowed = borrowed; }
}