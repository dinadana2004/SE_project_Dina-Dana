package service;
import domain.Admin;
import domain.User;
import domain.Book;
import domain.Loan;
import presentation.JsonAdminRepository;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * this service is responsible for checking whether the borrowing operation is accepted or not depending on whether the user has an overdue book or unpaid fine 
 * the borrowing operation is rejected if the user has an overdue book or unpaid fines
 * also this service is responsible for unregistering an inactive user ,this operation is only done by the administrator only , and the unregestered user should not have an overdue book or unpaid fines
 * */


public class BorrowUnderRestrictionds {
	private final JsonUserRepository userRepo;
    private final JsonBookRepository bookRepo;
    private final AdminService adminService;
    private final LoanService loanService;
    
    /**
     * Constructs a BorrowUnderRestrictionds with the provided user repository , book repository , adminService , loanService .
     *
     * @param userRepo    the repository that handles users
     * @param bookRepo    the repository that handles the books 
     * @param AdminService   the service that handles admin's operation
     * @param LoanService    the service that handles loan's funtions
     * 
     */

    public BorrowUnderRestrictionds(JsonUserRepository userRepo,
                           JsonBookRepository bookRepo,
                           AdminService adminService,
                           LoanService loanService) {
        this.userRepo = Objects.requireNonNull(userRepo);
        this.bookRepo = Objects.requireNonNull(bookRepo);
        this.adminService = Objects.requireNonNull(adminService);
        this.loanService = Objects.requireNonNull(loanService);
    }
    
    /**
     * this method checks whether the borrowing operation is accepted or rejected
     * @param username  the username  of the user who want to borrow
     * @param isbn the isbn of the book the user want to borrow
     * @return  a message describing whether borrowing was successful or failed*/
    
    
    public String borrowRestrictions(String username,String isbn) {
    	if(username==null||username.trim().isEmpty()) {
    		return"username shouldn't be empty";
    		
    	}
    	
    	if(isbn==null||isbn.trim().isEmpty()) {
    		return"isbn shouldn't be empty";
    		
    	}
    	
    	User user=userRepo.findUser(username);
    	if(user==null) {
    		return"this user does not exist";
    	}
    	if(user.hasUnpaidFines()) {
    		return"this borrowing operation has rejected because this user has unpaid fines,he/she should pay the fines first in order to borrow a book";
    		
    	}
    	
    	List<Book>overdue=loanService.getOverdueBooks(LocalDate.now());
    	boolean hasOverdue = overdue.stream()
                .anyMatch(b -> username.equalsIgnoreCase(b.getBorrowedByUser()));
    	if (hasOverdue) {
            return "User has overdue books and cannot borrow books.";
        }
    	
    	return loanService.borrowBook(username, isbn);
    	
    	
    }
    
    /**
     * this method is used to unrejister an inactive user that does not have unpaid fines or loans
     * @param username the username of the user which the admin want to unrejister
     * return message that describes whether the unrejister operation is accepted or rejected*/
    
    public String unregisterUser(String username) {
    	 if (!adminService.isLoggedIn())
             return "Only admins can unregister users.";
    	 
    	 if(username==null||username.trim().isEmpty()) {
     		return"username shouldn't be empty";
     		
     	}
    	 
    	 User user=userRepo.findUser(username);
     	if(user==null) {
     		return"this user does not exist";
     	}
     	if (user.hasUnpaidFines())
            return "User has unpaid fines and cannot be unregistered.";
     	
     	boolean isActive = bookRepo.findAll().stream()
                .anyMatch(b -> b.isBorrowed() && username.equalsIgnoreCase(b.getBorrowedByUser()));
        if (isActive)
            return "User has active loans and cannot be unregistered.";
        
        List<User> list = userRepo.findAll();
        list.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
        userRepo.update();

        return "User unregistered successfully.";
     	
     	
    	 
    }
    

	

}
