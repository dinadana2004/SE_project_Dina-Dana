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
 * this srevice is responsible for  unregistering an inactive user ,
 * this operation is only done by the administrator only , and the unregestered user should not have an overdue book or unpaid fines*/


public class UnregisterUser {
	private final JsonUserRepository userRepo;
    private final JsonBookRepository bookRepo;
    private final AdminService adminService;
    private final LoanService loanService;
    
    /**
     * Constructs an UnregisterUser with the provided user repository , book repository , adminService , loanService .
     *
     * @param userRepo    the repository that handles users
     * @param bookRepo    the repository that handles the books 
     * @param AdminService   the service that handles admin's operation
     * @param LoanService    the service that handles loan's funtions
     * 
     */



    public UnregisterUser(JsonUserRepository userRepo,
        JsonBookRepository bookRepo,
        AdminService adminService,
        LoanService loanService) {

    	this.userRepo = Objects.requireNonNull(userRepo);

    	this.bookRepo = Objects.requireNonNull(bookRepo);

    	this.adminService = Objects.requireNonNull(adminService);

    	this.loanService = Objects.requireNonNull(loanService);
  }
    
    /**
     * this method is used to unregister an inactive user that does not have unpaid fines or loans
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
