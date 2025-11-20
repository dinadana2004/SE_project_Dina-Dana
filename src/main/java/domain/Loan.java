package domain;


import java.time.LocalDate;


/**
* Represents a loan of a book by a user.
*/
public class Loan {
private final Book book;
private final User user;
private final LocalDate borrowDate;
private final LocalDate dueDate;


public Loan(Book book, User user, LocalDate borrowDate, LocalDate dueDate) {
this.book = book;
this.user = user;
this.borrowDate = borrowDate;
this.dueDate = dueDate;
}


public Book getBook() { 
	return book; }
public User getUser() {
	return user; }
public LocalDate getBorrowDate() { 
	return borrowDate; }
public LocalDate getDueDate() {
	return dueDate; }


public boolean isOverdue(LocalDate today) { return today.isAfter(dueDate); }
}