package service;

import domain.Book;
import domain.User;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Service that applies borrowing restrictions:
 * <ul>
 *     <li>User must exist.</li>
 *     <li>User must not have unpaid fines.</li>
 *     <li>User must not have overdue books.</li>
 * </ul>
 * If all checks pass, the actual borrowing is delegated to {@link LoanService}.
 */
public class BorrowUnderRestrictionds {   

    private final JsonUserRepository userRepo;
    private final JsonBookRepository bookRepo;
    private final AdminService adminService;
    private final LoanService loanService;

    /**
     * Constructs the service with required repositories and services.
     *
     * @param userRepo     user repository
     * @param bookRepo     book repository
     * @param adminService admin service (not currently used, but kept for future)
     * @param loanService  loan service handling book borrowing
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
     * Checks whether the borrowing operation is allowed for a given user and book ISBN.
     * <p>
     * Borrowing is rejected if:
     * <ul>
     *     <li>username or ISBN are empty</li>
     *     <li>user does not exist</li>
     *     <li>user has unpaid fines</li>
     *     <li>user has overdue books</li>
     * </ul>
     * Otherwise, the call is forwarded to {@link LoanService#borrowBook(String, String)}.
     *
     * @param username the username of the borrower
     * @param isbn     the ISBN of the book to borrow
     * @return a message describing the outcome
     */
    public String borrowRestrictions(String username, String isbn) {

        if (username == null || username.trim().isEmpty()) {
            return "username shouldn't be empty";
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            return "isbn shouldn't be empty";
        }

        User user = userRepo.findUser(username);
        if (user == null) {
            return "this user does not exist";
        }

        if (user.hasUnpaidFines()) {
            return "this borrowing operation has rejected because this user has unpaid fines,he/she should pay the fines first in order to borrow a book";
        }

        List<Book> overdue = loanService.getOverdueBooks(LocalDate.now());
        boolean hasOverdue = overdue.stream()
                .anyMatch(b -> username.equalsIgnoreCase(b.getBorrowedByUser()));
        if (hasOverdue) {
            return "User has overdue books and cannot borrow books.";
        }

        // Delegate to LoanService for the actual borrowing
        return loanService.borrowBook(username, isbn);
    }
}
    
    
     	
    	 
    
    

	


