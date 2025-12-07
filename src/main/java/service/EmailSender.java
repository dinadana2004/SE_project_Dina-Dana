package service;

/**
 * Interface for sending email messages.
 */
public interface EmailSender {

    /**
     * Sends an email to a given recipient.
     *
     * @param to      the receiver's email address
     * @param subject the subject of the email
     * @param body    the body content of the email
     * @return true if email sent successfully, otherwise false
     */
    boolean sendEmail(String to, String subject, String body);
}