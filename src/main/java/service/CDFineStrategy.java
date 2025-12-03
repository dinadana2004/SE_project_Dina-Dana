package service;

/**
 * Fine strategy for CDs: 20 NIS per overdue day.
 */
public class CDFineStrategy implements FineStrategy {

    @Override
    public int calculateFine(int daysOverdue) {
        if (daysOverdue <= 0) return 0;
        return daysOverdue * 20;
    }
}