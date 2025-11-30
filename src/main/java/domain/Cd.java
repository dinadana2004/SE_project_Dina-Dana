package domain;

import java.time.LocalDate;

/**
 * this class represents a CD  type in the library
 * this extends the Book class to reuse the method that are already exixst in the book class like borrowing for example,
 * but the Cd class uses a different loan period and fine strategy 
 *
 * Sprint 5 requirement:
 *  - CDs are borrowed for 7 days (handled in MediaService)
 *  - CDs have different fine strategy (20 NIS per overdue day)
 */

public class Cd extends Book {
	
	/**
	 * a constructor to constuct a new CD
	 * @param title the title of the cd
	 * @param author the author of the cd
	 * @param isbn the isbn(unique identifier) of the cd*/

    public Cd(String title, String author, String isbn) {
        super(title, author, isbn);
        this.setMediaType("CD");
    }
    
    /**
     * the borrowing method is inherited from Book Class
     * the loan's period here is different from the Book's Loan 
     * @param username the username of the user who want to borrow the CD
     * @param dueDate the date by which the user should return the CD back*/

    @Override
    public void borrow(String username, LocalDate dueDate) {
        super.borrow(username, dueDate); 
    }
}