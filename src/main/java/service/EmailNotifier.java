package service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Real email sender using Gmail SMTP & Jakarta Mail API.
 */
public class EmailNotifier implements EmailSender {

    private final String username;
    private final String password;

    public EmailNotifier(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean sendEmail(String to, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            jakarta.mail.Session session = jakarta.mail.Session.getInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            return true;

        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
            return false;
        }
    }
}
