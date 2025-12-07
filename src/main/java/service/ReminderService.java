package service;

import domain.Book;
import domain.User;
import presentation.JsonUserRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Sends overdue reminders to users who have overdue books.
 */
public class ReminderService {

    private final LoanService loanService;
    private final JsonUserRepository userRepo;
    private final EmailSender emailSender;

    public ReminderService(LoanService loanService,
                           JsonUserRepository userRepo,
                           EmailSender emailSender) {
        this.loanService = loanService;
        this.userRepo = userRepo;
        this.emailSender = emailSender;
    }

    /**
     * Sends reminder emails and returns number of emails successfully sent.
     */
    public int sendReminders() {
        int count = 0;

        for (User user : userRepo.findAll()) {

            List<Book> overdue = loanService.getOverdueBooks(LocalDate.now());

            // Keep only this user's overdue books
            overdue.removeIf(b -> !user.getUsername().equals(b.getBorrowedByUser()));

            if (!overdue.isEmpty()) {

                String msg = "You have " + overdue.size() + " overdue book(s).";

                boolean sent = emailSender.sendEmail(
                        user.getEmail(),
                        "Library Reminder",
                        msg
                );

                if (sent) count++;
            }
        }

        return count;
    }
}