package service;
import domain.Book;

import domain.CD;
import domain.OverDueReport;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OverDueReportTest {

    @Test
    void testConstructorAndGetters() {

        Book b1 = new Book("Java", "James", "111");
        CD c1 = new CD("Music", "Artist");

        OverDueReport report = new OverDueReport(
                List.of(b1),
                List.of(c1),
                30
        );

        assertEquals(1, report.getOverdueBooks().size());
        assertEquals(1, report.getOverdueCDs().size());
        assertEquals(30, report.getTotalFine());

        assertEquals(b1, report.getOverdueBooks().get(0));
        assertEquals(c1, report.getOverdueCDs().get(0));
    }
}
