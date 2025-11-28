package service;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock implementation of EmailSender used for testing.
 * This mock does not send real emails; instead, it stores messages
 * in memory for verification.
 *
 * @author Dana
 * @version 1.0
 */
public class MockEmailSender implements EmailSender {

    private final List<String> logs = new ArrayList<>();

    @Override
    public boolean sendEmail(String to, String subject, String body) {
        logs.add(to + " | " + subject + " | " + body);
        return true;
    }

    /**
     * Returns a list of all recorded email messages.
     *
     * @return list of sent messages (simulated)
     */
    public List<String> getLogs() {
        return logs;
    }
}
