package service;

import domain.Book;
import domain.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReminderServiceTest {

    @Test
    void testReminderWithMock() {

        EmailSender mockSender = mock(EmailSender.class);
        when(mockSender.sendEmail(anyString(), anyString(), anyString()))
                .thenReturn(true);

        FakeUserRepository userRepo = new FakeUserRepository();
        FakeBookRepository bookRepo = new FakeBookRepository();

        User u = new User("dina", "123", "dina@gmail.com");
        userRepo.save(u);

        Book b = new Book("Java", "James", "111");
        b.borrow("dina", LocalDate.now().minusDays(30));
        bookRepo.save(b);

        LoanService loanService = new LoanService(bookRepo, userRepo);
        ReminderService reminder = new ReminderService(loanService, userRepo, mockSender);

        int result = reminder.sendReminders();
        assertEquals(1, result);

        verify(mockSender, times(1))
                .sendEmail("dina@gmail.com",
                        "Library Reminder",
                        "You have 1 overdue book(s).");
    }
}
