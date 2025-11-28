package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Sends real emails using Gmail SMTP service.
 * This implementation is used in the live application (not in tests).
 *
 * <p>Requires Gmail App Password (not regular password).</p>
 *
 * @author Dana
 * @version 1.0
 */
public class EmailNotifier implements EmailSender {

    private final String fromEmail;
    private final String appPassword;

    /**
     * Constructs a real email sender using the provided Gmail address and
     * Gmail App Password.
     *
     * @param fromEmail   sender Gmail address
     * @param appPassword Gmail App Password
     */
    public EmailNotifier(String fromEmail, String appPassword) {
        this.fromEmail = fromEmail;
        this.appPassword = appPassword;
    }

    /**
     * Sends a real email using Gmail SMTP.
     *
     * @param to      receiver email address
     * @param subject email subject line
     * @param body    email body content
     * @return true if email successfully sent, false otherwise
     */
    @Override
    public boolean sendEmail(String to, String subject, String body) {
        try {
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "587");
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(p, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, appPassword);
                }
            });

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);

            Transport.send(msg);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
