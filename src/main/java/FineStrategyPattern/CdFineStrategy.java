package FineStrategyPattern;
/**
 * this is a CD fine's Strategy it is baesd on overdueDays
 * a fine of 20 NIS per overdue day.
 */
public class CdFineStrategy implements FineStrategy{
	/**
	 * this method is for calculating the fines based on overdueDays
	 * @param overdueDays the number of days that overdued the date by which the CD must be returned back
	 * @return  overdueDays*20 the amount of fine the borrower should pay*/
	@Override
    public int calculateFine(int overdueDays) {
        return overdueDays * 20;
    }

}
