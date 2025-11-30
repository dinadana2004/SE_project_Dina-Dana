package service;

import domain.Book;
import domain.User;
import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing book loan operations such as borrowing books,
 * checking overdue status, retrieving overdue books, and handling fine payments.
 *
 * <p>This service communicates with {@link JsonBookRepository} and
 * {@link JsonUserRepository} for persistent data operations.</p>
 *
 * @author Dana
 * @version 1.0
 */
public class LoanService {

    /** Repository used for accessing and updating book data. */
    private JsonBookRepository bookRepo;

    /** Repository used for accessing and updating user data. */
    private JsonUserRepository userRepo;

    /**
     * Constructs a LoanService with the required repositories.
     *
     * @param bookRepo the repository managing books
     * @param userRepo the repository managing users
     */
    public LoanService(JsonBookRepository bookRepo, JsonUserRepository userRepo) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    /**
     * Borrows a book using the current system date.
     *
     * @param username the username of the borrower
     * @param isbn     the ISBN of the book to borrow
     * @return a message describing the borrowing result
     */
    public String borrowBook(String username, String isbn) {
        return borrowBook(username, isbn, LocalDate.now());
    }

    /**
     * Borrows a book on a specified date (used mainly for testing).
     *
     * @param username the username of the borrower
     * @param isbn     the ISBN of the book
     * @param today    the date on which the book is borrowed
     * @return a status message describing success or failure
     */
    public String borrowBook(String username, String isbn, LocalDate today) {

        if (username == null || isbn == null ||
                username.trim().isEmpty() || isbn.trim().isEmpty()) {
            return "Username and ISBN cannot be empty!";
        }

        User user = userRepo.findUser(username);
        if (user == null)
            return "User not found!";

        if (user.hasUnpaidFines())
            return "User has unpaid fines and cannot borrow books.";

        Book book = bookRepo.findByIsbn(isbn);
        if (book == null)
            return "Book not found!";

        if (book.isBorrowed())
            return "Book is already borrowed!";

        LocalDate due = today.plusDays(28);
        book.borrow(username, due);
        bookRepo.update();

        return "Book borrowed successfully! Due date: " + due;
    }

    /**
     * Checks whether a book is overdue using the current system date.
     *
     * @param book the book to check
     * @return {@code true} if overdue, otherwise {@code false}
     */
    public boolean isBookOverdue(Book book) {
        return isBookOverdue(book, LocalDate.now());
    }

    /**
     * Checks whether a book is overdue relative to a specified date.
     *
     * @param book  the book being checked
     * @param today the date used for comparison
     * @return {@code true} if the book is overdue, otherwise {@code false}
     */
    public boolean isBookOverdue(Book book, LocalDate today) {
        if (book == null) return false;
        if (!book.isBorrowed()) return false;
        if (book.getDueDate() == null) return false;
        return today.isAfter(book.getDueDate());
    }

    /**
     * Retrieves all books that are overdue relative to a given date.
     *
     * @param today the date used for overdue comparison
     * @return a list of overdue books
     */
    public List<Book> getOverdueBooks(LocalDate today) {
        List<Book> list = new ArrayList<>();
        for (Book b : bookRepo.findAll()) {
            if (isBookOverdue(b, today))
                list.add(b);
        }
        return list;
    }

    /**
     * Processes a fine payment for a specific user.
     *
     * @param username the username of the user paying the fine
     * @param amount   the amount being paid
     * @return a message describing the result of the payment
     */
    public String payFine(String username, int amount) {

        if (username == null || username.trim().isEmpty())
            return "Username cannot be empty!";

        if (amount <= 0)
            return "Payment amount must be positive!";

        User user = userRepo.findUser(username);
        if (user == null)
            return "User not found!";

        int before = user.getFineBalance();
        if (before == 0)
            return "User has no fines to pay.";

        user.payFine(amount);
        userRepo.update();

        int after = user.getFineBalance();

        if (after > 0)
            return "Partial payment accepted. Remaining fine: " + after + " NIS.";

        return "Fine fully paid. Remaining fine: 0 NIS.";
    }
    
   
}
