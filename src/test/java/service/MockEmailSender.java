package service;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock email sender used during testing (no real emails sent).
 */
public class MockEmailSender implements EmailSender {

    private final List<String> logs = new ArrayList<>();

    @Override
    public boolean sendEmail(String to, String subject, String body) {
        logs.add(to + " | " + subject + " | " + body);
        return true;
    }

    public List<String> getLogs() {
        return logs;
    }
}