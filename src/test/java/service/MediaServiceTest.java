
import domain.BookMedia;

import domain.CD;
import domain.Media;
import org.junit.jupiter.api.Test;
import service.MediaService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MediaServiceTest {

    @Test
    void borrowCD_forSevenDays_US51() {
        MediaService service = new MediaService();
        CD cd = new CD("Top Hits", "Artist");
        service.addMedia(cd);

        LocalDate today = LocalDate.of(2025, 1, 1);
        String result = service.borrowMedia("dana", "Top Hits", today);

        assertTrue(result.contains("Due date:"));
        assertTrue(cd.isBorrowed());
        assertEquals("dana", cd.getBorrowedByUser());
        assertEquals(today.plusDays(7), cd.getDueDate());
    }

    @Test
    void borrowBook_forTwentyEightDays() {
        MediaService service = new MediaService();
        BookMedia book = new BookMedia("Java", "James", "111");
        service.addMedia(book);

        LocalDate today = LocalDate.of(2025, 1, 1);
        String result = service.borrowMedia("dina", "Java", today);

        assertTrue(result.contains("Due date:"));
        assertTrue(book.isBorrowed());
        assertEquals("dina", book.getBorrowedByUser());
        assertEquals(today.plusDays(28), book.getDueDate());
    }

    @Test
    void borrowAlreadyBorrowedMedia_shouldFail() {
        MediaService service = new MediaService();
        CD cd = new CD("Top Hits", "Artist");
        service.addMedia(cd);

        LocalDate today = LocalDate.of(2025, 1, 1);
        service.borrowMedia("dana", "Top Hits", today);
        String second = service.borrowMedia("dina", "Top Hits", today.plusDays(1));

        assertEquals("Media is already borrowed!", second);
    }

    @Test
    void borrowNonExistingMedia_shouldReturnNotFound() {
        MediaService service = new MediaService();
        String result = service.borrowMedia("dana", "Unknown", LocalDate.now());
        assertEquals("Media not found!", result);
    }
}
