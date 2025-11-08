import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domain.Book;
import service.BookService;


public class addingNewBook_testing {
	
	@Test
	public void valid_information() {
		Book book=new Book("science","john","100");
		BookService service=new BookService(book);
		String result=service.add_NewBook(book);
		assertEquals("book added successfully", result);
	}
	
	@Test
	public void missing_title() {
		Book book=new Book(null,"john","100");
		BookService service=new BookService(book);
		String result=service.add_NewBook(book);
		assertEquals("you should enter the title,author and isbn", result);
	}
	
	@Test
	public void missing_author() {
		Book book=new Book("math",null,"100");
		BookService service=new BookService(book);
		String result=service.add_NewBook(book);
		assertEquals("you should enter the title,author and isbn", result);
	}
	
	@Test
	public void missing_isbn() {
		Book book=new Book("math","john",null);
		BookService service=new BookService(book);
		String result=service.add_NewBook(book);
		assertEquals("you should enter the title,author and isbn", result);
	}
	
	@Test
	public void duplicate_isbn() {
		Book book1=new Book("java","jack","100");
		BookService service=new BookService(book1);
		String result=service.add_NewBook(book1);
		Book book2=new Book("c++","David","100");
		String result2=service.add_NewBook(book2);
		assertEquals("you have entered a duplicate ISBN ,please enter another ISBN", result2);
	}
	
	
	
	
	

}
