package domain;



/**
 * Strategy interface for calculating fines for overdue media items.
 */
public interface FineStrategy {

    /**
     * Calculates the fine amount based on how many days the item is overdue.
     *
     * @param daysOverdue number of overdue days
     * @return fine amount in NIS
     */
    int calculateFine(int daysOverdue);
}