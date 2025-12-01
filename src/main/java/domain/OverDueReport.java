package domain;
import java.util.List;
/**
 * this is responsible for generating a report that summaries what  book or CD is overdued for a user
 * this report give you the total fines you must pay*/
public class OverDueReport {
	 private final List<Book> overdueBooks = null;
	    private final List<Cd> overdueCDs = null;
	    private final int totalFine = 0;
	    
	    /**
	     * constructor to construc a report that reprsents the total fines
	     * @param overdueBooks list of overdue books
	     * @param overdueCDs list of overdue CD
	     * @param totalFine combined fine for all media types(CD,Book)
	     * @return 
	     *  */
	    public OverdueReport(List<Book> overdueBooks, List<CD> overdueCDs, int totalFine) {
	        this.overdueBooks = overdueBooks;
	        this.overdueCDs = overdueCDs;
	        this.totalFine = totalFine;
	    }
	    /** @return overdue books */
	    public List<Book> getOverdueBooks() { return overdueBooks; }

	    /** @return overdue CDs */
	    public List<Cd> getOverdueCDs() { return overdueCDs; }

	    /** @return total fine amount */
	    public int getTotalFine() { return totalFine; }

}
