package service;
import domain.FineStrategy;

/**
 * Fine strategy for books: 10 NIS per overdue day.
 */
public class BookFineStrategy implements FineStrategy {

    @Override
    public int calculateFine(int daysOverdue) {
        if (daysOverdue <= 0) return 0;
        return daysOverdue * 10;
    }
}