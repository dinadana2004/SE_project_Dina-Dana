package service;



import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmailNotifierTest {

    @Test
    void testSendEmailSuccess() throws Exception {

        EmailNotifier notifier = new EmailNotifier("test@gmail.com", "appPass");

        // Mock SMTP send
        try (MockedStatic<Transport> transportMock = mockStatic(Transport.class)) {

            boolean result = notifier.sendEmail("dana@mail.com", "Hello", "Message");

            transportMock.verify(() -> Transport.send(any(Message.class)));
            assertTrue(result);
        }
    }

    @Test
    void testSendEmailFailure() {
        EmailNotifier notifier = new EmailNotifier("bad@mail.com", "wrong");

        try (MockedStatic<Transport> transportMock = mockStatic(Transport.class)) {

            // Simulate SMTP failure
            transportMock.when(() -> Transport.send(any(Message.class)))
                    .thenThrow(new RuntimeException("SMTP Error"));

            boolean result = notifier.sendEmail("dana@mail.com", "Hello", "body");

            assertFalse(result);
        }
    }
}