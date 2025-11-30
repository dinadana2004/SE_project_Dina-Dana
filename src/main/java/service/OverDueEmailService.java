package service;
import domain.User;
/**
 *this  Service is  responsible for sending  emails to users who has overdued

 */

public class OverDueEmailService {
	private final OverdueService overdueService;
    private final EmailService emailService;
    

/**
 * this is a constuctor */
    public ReminderService(OverdueService overdueService, EmailService emailService) {
        this.overdueService = overdueService;
        this.emailService = emailService;
    }
    
    public void sendReminderEmail(User user) {
        int overdueCount = overdueService.getOverdueCount(user);

        String message = "You have " + overdueCount + " overdue book(s).";

        emailService.sendEmail(user.getEmail(), message);
    }
	

}
