package service;

import domain.Media;
import domain.OverdueItem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Service that generates overdue reports and fines for mixed media types.
 */
public class OverdueReportService {

    private final List<Media> mediaList;

    /**
     * Creates a new report service bound to a list of media items.
     *
     * @param mediaList list of media items to inspect
     */
    public OverdueReportService(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    /**
     * Generates a list of overdue items (books and CDs) with their fines.
     *
     * @param today the current date
     * @return list of overdue items with fines
     */
    public List<OverdueItem> generateReport(LocalDate today) {

        List<OverdueItem> result = new ArrayList<>();

        for (Media m : mediaList) {
            if (!m.isBorrowed() || m.getDueDate() == null) {
                continue;
            }

            if (today.isAfter(m.getDueDate())) {
                long days = ChronoUnit.DAYS.between(m.getDueDate(), today);
                int daysOverdue = (int) days;
                if (daysOverdue <= 0) continue;

                FineStrategy strategy =
                        "CD".equals(m.getMediaType())
                                ? new CDFineStrategy()
                                : new BookFineStrategy();

                int fine = strategy.calculateFine(daysOverdue);
                result.add(new OverdueItem(m, fine));
            }
        }

        return result;
    }

    /**
     * Calculates the total fine amount for a list of overdue items.
     *
     * @param items list of overdue items
     * @return total fine in NIS
     */
    public int calculateTotalFine(List<OverdueItem> items) {
        int sum = 0;
        for (OverdueItem item : items) {
            sum += item.getFineAmount();
        }
        return sum;
    }
}