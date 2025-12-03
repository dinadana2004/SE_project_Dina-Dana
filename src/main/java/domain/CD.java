package domain;

/**
 * Represents a CD media item with a fixed 7-day borrowing period.
 */
public class CD extends Media {

    private String artist;

    /**
     * Creates a CD media item.
     *
     * @param title  CD title
     * @param artist CD artist or band
     */
    public CD(String title, String artist) {
        super(title);
        this.artist = artist;
    }

    @Override
    public int getBorrowDays() {
        return 7; // US5.1: CDs can be borrowed for 7 days
    }

    @Override
    public String getMediaType() {
        return "CD";
    }

    public String getArtist() {
        return artist;
    }
}