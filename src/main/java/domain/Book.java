package domain;

import java.sql.Date;
import java.time.LocalDate;

/**
 *  this class Represents a Book which an admintrator can add search or borrow.
 *  
 * 
 * 
 * @author Dina
 * @version 1.0
 */

public class Book {
	private String title;
	private String author;
	private String isbn;
	private boolean available;
	private boolean isBorrowedOrNot;
	private LocalDate dueDate;
	
	/**
	 * constructs a Book with it's specified Title ,Authors'name and Isbn*/
	
	public Book(String title,String author,String isbn) {
		this.title=title;
		this.author=author;
		this.isbn=isbn;
		this.available = true;
	
	}
	/**
	 * @return books'Title*/
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * @return books'Author*/
	
	public String getAuthor() {
		return author;
	}
	
	/**
	 * @return books'Isbn*/
	
	public String getIsbn() {
		return isbn;
	}
	
	/** 
	 * this function return the value of isBorrowedOrNot
	 * @return isBorrowedOrNot*/
	
	public Boolean getisBorrowedOrNot() {
		return isBorrowedOrNot;
	}
	
	public LocalDate getdueDate() {
		return dueDate;
	}
	
	/**
	 * this function handles the operation of borrowing book
	 * set the value of isBorrowedOrNot to true indicating that the book now is borrowed 
	 * set the dueDate to the value which equals to =borrow day'sDate+ 28 days */
	
	public void wantToBorrowBook() {
		this.isBorrowedOrNot=true;
		this.dueDate=LocalDate.now().plusDays(28);
	}
	
	/**
	 * this function handles the operation of returning book back
	 * set the value of isBorrowedOrNot to false indicating that the book now is  not borrowed */
	
	public void wantToReturnBook() {
		this.isBorrowedOrNot=false;
		this.dueDate=null;
		
	}
	
	
	/**
     * Returns a formatted string representation of the book.
     * This method overrides {@code toString()} to display book details neatly.
     * 
     * @return a string containing the title, author, and ISBN of the book
     */
    @Override
    public String toString() {
        return String.format("📘 Title: %s | ✍️ Author: %s | 🔢 ISBN: %s", title, author, isbn);
    }
	
	


}
