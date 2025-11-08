import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import domain.Book;
import service.BookService;
public class borrowBookTesting {
	@Test
	public void borrowBookTest() {
		Book book=new Book("biology","yousef","100");
		BookService service=new BookService();
		 String result=service.borrowBook(book);
		assertEquals("book borrowed successfully,you can have this book till" +book.getdueDate(),result);
	}
	@Test
	public void alreadyBorrowedTest() {
		Book book=new Book("biology","yousef","100");
		BookService service=new BookService();
		 String result=service.borrowBook(book);
		assertEquals("you can not borrow this book",result);
	}
	
	@Test
	public void returnBookTest() {
		Book book=new Book("biology","yousef","100");
		BookService service=new BookService();
		 String result=service.returnBook(book);
		assertEquals("book returned successfully,thank you for returning the book back within the expected time ",result);
	}
	
	@Test
	public void alreadyReturnTest() {
		Book book=new Book("biology","yousef","100");
		BookService service=new BookService();
		 String result=service.returnBook(book);
		assertEquals("this book is already returned",result);
	}
	
	public void ReturnbookNotBorrowedTest() {
		Book book=new Book("science","ali","102");
		BookService service=new BookService();
		 String result=service.returnBook(book);
		assertEquals("this book is already returned",result);
	}
	
	
	
	
	
	

}
