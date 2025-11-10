package service;




/**
 * this handles the services of the Book like searching and adding new Book
 * @author Dina
 * @version 1.0*/

import domain.Book;
import java.util.ArrayList;
import java.util.List;
public class BookService {
	
	 /** The current book instance associated with this service. */
	private Book book;
	
	 /** The list of all books managed by the service. */
	private List<Book>books=new ArrayList<>();
	
	
	/**
	 * constructs a Book
	 * @param  book the initial book instance to associate with the service */
	
	public BookService(Book book) {
	    this.book = book;
	    this.books = new ArrayList<>();
	}

	public BookService() {
	    this.books = new ArrayList<>();
	}

	
	/**
	 * this function handles the operation of adding a new book
	 * @param book
	 * @return  a confirmation message for successful addition */
	public String  add_NewBook( Book book) {
		
		if(book.getAuthor()==null || book.getTitle()==null || book.getIsbn()==null) {
			return "you should enter the title,author and isbn";
		}
		for(Book book1:books) {
			if(book.getIsbn()==book1.getIsbn()) {
				return"you have entered a duplicate ISBN ,please enter another ISBN";
			}
		}
		
		books.add(book);
		return "book added successfully";
	}
	
	/**
	 * handles the operation of searching a book by it's Author's Name
	 * @param author the author's name to search for
	 * @return list of book that match the provided author name*/
	
	public List<Book> search_book_author(String author) {
		List<Book> searchingResult=new ArrayList<>();
		for(Book book:books) {
			if(book.getAuthor().equalsIgnoreCase(author)) {
				searchingResult.add(book);
				
			}
			
			
		}
		return searchingResult;
		
	}
	
	/**
	 * handles the operation of searching a book by it's Title 
	 * @param title the Book's Title
	 * @return list of book that match the provided Book's Title*/
	
	public List<Book> search_book_title(String title) {
		List<Book> searchingResult=new ArrayList<>();
		for(Book book:books) {
			if(book.getTitle().equalsIgnoreCase(title)) {
				
			}
			
		}
		return searchingResult;
	}
	
	
	/**
	 * handles the operation of searching a book by it's Isbn 
	 * @param isbn the Book's isbn
	 * @return list of book that match the provided Book's isbn*/
	
	public Book search_book_isbn(String isbn) {
		
		for(Book book:books) {
			if(book.getIsbn().equalsIgnoreCase(isbn)) {
				return book;
			}
			
		}
		return null;
	}
	
	/**
	 * @param book the book user want to borrow
	 * @return confirmation message that shows the dueDate the user should return the book back within */
	
	
	public String borrowBook(Book book) {
		if(book.getisBorrowedOrNot()) {
			return"you can not borrow this book";
		}
		else {
			book.wantToBorrowBook();
			return"book borrowed successfully,you can have this book till" +book.getdueDate();
			
		}
		
	}
	
	/** 
	 *@param book the book user want to return
	 *@return confirmation message the ensure that the book has returned */
	
	public String returnBook(Book book) {
		if(book.getisBorrowedOrNot()) {
			book.wantToReturnBook();
			return"book returned successfully,thank you for returning the book back within the expected time ";
			
		}
		else {
			return"this book is already returned";
		}
		
	}

}
