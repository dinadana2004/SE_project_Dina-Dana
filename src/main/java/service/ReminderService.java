package service;

import domain.Book;
import domain.User;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Service responsible for sending reminder emails to users with overdue books.
 * This service integrates LoanService, UserRepository, and an EmailSender
 * (real or mocked).
 *
 * @author Dana
 * @version 1.0
 */
public class ReminderService {

    private final LoanService loanService;
    private final JsonUserRepository userRepo;
    private final EmailSender emailSender;

    /**
     * Constructs a new ReminderService.
     *
     * @param loanService  the service handling loan-related logic
     * @param userRepo     repository storing all users
     * @param emailSender  the email sender implementation to use
     */
    public ReminderService(LoanService loanService, JsonUserRepository userRepo, EmailSender emailSender) {
        this.loanService = loanService;
        this.userRepo = userRepo;
        this.emailSender = emailSender;
    }

    /**
     * Sends reminder emails to all users who currently have overdue books.
     *
     * @return number of successfully delivered emails
     */
    public int sendReminders() {
        int count = 0;

        for (User user : userRepo.findAll()) {
            List<Book> overdue = loanService.getOverdueBooks(LocalDate.now());
            overdue.removeIf(b -> !user.getUsername().equals(b.getBorrowedByUser()));

            if (!overdue.isEmpty()) {
                String msg = "You have " + overdue.size() + " overdue book(s).";
                boolean sent = emailSender.sendEmail(user.getEmail(),
                        "Library Reminder",
                        msg);

                if (sent) count++;
            }
        }

        return count;
    }
}
