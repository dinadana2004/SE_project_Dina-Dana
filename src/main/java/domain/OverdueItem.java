package domain;

/**
 * Represents a media item that is overdue along with its calculated fine.
 */
public class OverdueItem {

    private final Media media;
    private final int fineAmount;

    /**
     * Creates a new overdue item entry.
     *
     * @param media      the overdue media item
     * @param fineAmount the fine amount in NIS
     */
    public OverdueItem(Media media, int fineAmount) {
        this.media = media;
        this.fineAmount = fineAmount;
    }

    /**
     * @return the overdue media item
     */
    public Media getMedia() {
        return media;
    }

    /**
     * @return the fine amount in NIS
     */
    public int getFineAmount() {
        return fineAmount;
    }
}