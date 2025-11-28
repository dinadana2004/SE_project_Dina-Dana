package service;

/**
 * Interface representing an email sending mechanism.
 * Implementations may send real emails or simulate email sending (for tests).
 *
 * @author Dana
 * @version 1.0
 */
public interface EmailSender {

    /**
     * Sends an email to a recipient.
     *
     * @param to      receiver email address
     * @param subject email subject line
     * @param body    email body content
     * @return true if sending was successful, false otherwise
     */
    boolean sendEmail(String to, String subject, String body);
}
