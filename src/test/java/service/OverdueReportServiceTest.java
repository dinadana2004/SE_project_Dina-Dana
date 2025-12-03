package service;

import domain.BookMedia;
import domain.CD;
import domain.Media;
import domain.OverdueItem;
import org.junit.jupiter.api.Test;
import service.OverdueReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OverdueReportServiceTest {

    @Test
    void noOverdueItems_returnsEmptyList() {
        List<Media> list = new ArrayList<>();
        CD cd = new CD("Top Hits", "Artist");
        list.add(cd);

        LocalDate today = LocalDate.of(2025, 1, 10);

        OverdueReportService reportService = new OverdueReportService(list);
        List<OverdueItem> result = reportService.generateReport(today);

        assertTrue(result.isEmpty());
    }

    @Test
    void singleCDOverdue_correctFine() {
        List<Media> list = new ArrayList<>();
        CD cd = new CD("Top Hits", "Artist");
        list.add(cd);

        LocalDate due = LocalDate.of(2025, 1, 1);
        LocalDate today = LocalDate.of(2025, 1, 4); // 3 days overdue
        cd.borrow("dana", due);

        OverdueReportService reportService = new OverdueReportService(list);
        List<OverdueItem> result = reportService.generateReport(today);

        assertEquals(1, result.size());
        OverdueItem item = result.get(0);
        assertEquals(60, item.getFineAmount()); // 3*20
    }

    @Test
    void mixedMedia_overdueReportHasCorrectTotalFine_US53() {
        List<Media> list = new ArrayList<>();

        BookMedia book = new BookMedia("Java", "James", "111");
        CD cd = new CD("Top Hits", "Artist");

        LocalDate today = LocalDate.of(2025, 1, 10);

        // Book overdue 2 days
        LocalDate bookDue = today.minusDays(2);
        book.borrow("dina", bookDue);

        // CD overdue 3 days
        LocalDate cdDue = today.minusDays(3);
        cd.borrow("dana", cdDue);

        list.add(book);
        list.add(cd);

        OverdueReportService reportService = new OverdueReportService(list);
        List<OverdueItem> result = reportService.generateReport(today);

        assertEquals(2, result.size());

        int total = reportService.calculateTotalFine(result);
        // Book: 2 * 10 = 20, CD: 3 * 20 = 60 → total = 80
        assertEquals(80, total);
    }
}