package FineStrategyPattern;
/**
 * this  strategy interface for calculating overdue fines.
 * it allows adding new media types without modifying existing logic or the code.
 */

public interface FineStrategy {
	/**
    * Calculates the fine based on overdue days.
    *
    * @param overdueDays number of overdue days
    * @return fine amount in NIS
    */
	int calculateFines(int numberOfOverDueDays);

}


