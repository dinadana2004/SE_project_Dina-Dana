package FineStrategyPattern;
/**
 * this is a Book fine's Strategy it is baesd on overdueDays
 * a fine of 10 NIS per overdue day.
 */

public class BookFineStrategy implements FineStrategy {
	/**
	 * this method is for calculating the fines based on overdueDays
	 * @param overdueDays the number of days that overdued the date by which the Book must be returned back
	 * @return  overdueDays*10 the amount of fine the borrower should pay*/
	 @Override
	    public int calculateFine(int overdueDays) {
	        return overdueDays * 10;
	    }

}
