package service;
import domain.User;

import domain.Loan;
import java.time.Clock;
import java.time.LocalDate;

/**
 * Service responsible for calculating how many loans are overdue for a given user.
 *
 * <p>This service uses a {@link Clock} instance to obtain the current date. Using a {@code Clock}
 * makes the service testable because tests can provide a fixed clock to simulate different dates.</p>
 *
 * @author Dina
 * @version 1.0
 */


public class OverDueBook {
	 /** Clock used to determine the current date (injectable for testing). */
	private final Clock clock;
	
	
	/**
     * Constructs an OverdueService that uses the given clock to obtain the current date.
     *
     * @param clock the clock to use 
     *              
     */
	
	overDueBook(Clock clock){
		this.clock=clock;
	}
	
	
	 /**
     * Counts the number of overdue loans for the provided user.
     *
     * <p>The method queries the user's loans and counts those for which
     * {@link Loan#isOverdue(LocalDate)} returns {@code true} using the current date
     * obtained from the injected {@link Clock}.</p>
     *
     * @param user the user whose loans will be checked (must not be {@code null})
     * @return the number of overdue loans (0 if none or if the user has no loans)
     * @throws NullPointerException if {@code user} is {@code null} or {@code user.getLoans()} is {@code null}
     */
	public int getOverDueCount(User user) {
		LocalDate today=LocalDate.now(clock);
		return (int) user.getLoans().stream()
                .filter(loan -> loan.isOverdue(today))  // ← استخدم method الجديدة
                .count();
	}
	
	/**
	 *  this ia an Interface for sending emails to user who has overdued . 
	 *  it sends real emails.
	 * In testing we mock it to avoid sending real emails.
	 */
	public interface EmailService {
	    void sendEmail(String to, String message);
	}
	
	
	/**
	 *we will use  Mocking to test the email 
	 * It records all messages instead of sending them so the email is not sending in a real.
	 */
	public class MockEmailService implements EmailService {

	    private final List<String> sentMessages = new ArrayList<>();

	    @Override
	    public void sendEmail(String to, String message) {
	        sentMessages.add("To: " + to + ", Message: " + message);
	    }

	    public List<String> getSentMessages() {
	        return sentMessages;
	    }
	

}
