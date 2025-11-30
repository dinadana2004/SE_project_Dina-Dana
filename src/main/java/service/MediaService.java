package service;
import domain.Book;
import domain.User;
import domain.Cd;
import org.junit.jupiter.api.Test;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * this service handles the opreration of borrowing a CD*/

public class MediaService {
	private final JsonUserRepository userRepo;
    private final JsonBookRepository bookRepo;
    private final LoanService loanService;
    
    /**
     * contuctor to constructs a new mMediaService object
     * @param userRepo the repository of the user
     * @param bookRepo the CD repository
     * @param loanService the loan repository*/
    
    public MediaService(JsonUserRepository userRepo,JsonBookRepository bookRepo,LoanService loanService) {
    	this.bookRepo=bookRepo;
    	this.loanService=loanService;
    	this.userRepo=userRepo;
    }
    
    /**
     * this method is used for borrowing CD operation
     * @param username the username of the user who want to borrow the cd
     * @param isbn the isbn of the cd which will be borrowed
     * @return a message describing whether the operation succeeded or not*/
    
    public String BorrowCD(String username,String isbn) {
    	User user=userRepo.findUser(username);
    	if(user==null) {
    		return"this user does not exist";
    	}
    	Object media = bookRepo.findByIsbn(isbn);
        if (!(media instanceof CD))
            return "CD not found.";
        Cd cd = (CD) media;
        if(cd.isBorrowed()) {
        	return"this CD is currently Borrowed";
        }
        LocalDate dueDate = LocalDate.now().plusDays(7);
        cd.borrow(username, dueDate);
        bookRepo.update();

        return "CD borrowed successfully you should bringthe CD back till Due date: " + dueDate;
    }
    

}
