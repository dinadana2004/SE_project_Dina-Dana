package service;

import domain.Book;
import domain.User;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

/**
 * Service responsible for counting overdue books for a specific user.
 *
 * <p>This service uses a {@link Clock} to obtain the current date, which allows
 * easier testing by injecting a fixed clock.</p>
 */
public class OverDueBook {

    private final Clock clock;
    private final LoanService loanService;

    /**
     * Constructs an OverDueBook service with a given clock and loan service.
     *
     * @param clock       the clock used to obtain today's date
     * @param loanService service used to retrieve overdue books
     */
    public OverDueBook(Clock clock, LoanService loanService) {
        this.clock = clock;
        this.loanService = loanService;
    }

    /**
     * Counts the number of overdue books for the given user.
     *
     * @param user user whose overdue books will be checked
     * @return number of overdue books
     */
    public int getOverDueCount(User user) {

        LocalDate today = LocalDate.now(clock);

        List<Book> overdueBooks = loanService.getOverdueBooks(today);

        return (int) overdueBooks.stream()
                .filter(book -> book.getBorrowedByUser() != null)
                .filter(book -> book.getBorrowedByUser().equalsIgnoreCase(user.getUsername()))
                .count();
    }

    /**
     * Interface for sending emails to users with overdue books.
     */
    public interface EmailService {
        void sendEmail(String to, String message);
    }

    /**
     * Mock email service used only for testing.
     */
    public static class MockEmailService implements EmailService {

        private final List<String> sentMessages = new ArrayList<>();

        @Override
        public void sendEmail(String to, String message) {
            sentMessages.add("To: " + to + ", Message: " + message);
        }

        public List<String> getSentMessages() {
            return sentMessages;
        }
    }
}