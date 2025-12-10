package domain;

import java.util.List;

/**
 * Represents an overdue media report containing overdue books, overdue CDs,
 * and the total fine amount owed by the user.
 *
 * @author Dana
 * @version 1.0
 */
public class OverDueReport {

    private final List<Book> overdueBooks;
    private final List<CD> overdueCDs;
    private final int totalFine;

    /**
     * Constructs an OverDueReport containing all overdue media for a user.
     *
     * @param overdueBooks list of overdue books
     * @param overdueCDs list of overdue CDs
     * @param totalFine total fine owed (books + CDs)
     */
    public OverDueReport(List<Book> overdueBooks, List<CD> overdueCDs, int totalFine) {
        this.overdueBooks = overdueBooks;
        this.overdueCDs = overdueCDs;
        this.totalFine = totalFine;
    }

    /** @return overdue books */
    public List<Book> getOverdueBooks() { return overdueBooks; }

    /** @return overdue CDs */
    public List<CD> getOverdueCDs() { return overdueCDs; }

    /** @return total fine amount */
    public int getTotalFine() { return totalFine; }
}