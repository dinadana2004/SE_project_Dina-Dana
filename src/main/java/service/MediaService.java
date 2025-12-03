package service;

import domain.Media;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing media items (books, CDs) and borrowing operations.
 */
public class MediaService {

    private final List<Media> mediaList = new ArrayList<>();

    /**
     * Adds a media item to the system.
     *
     * @param m the media item to add
     */
    public void addMedia(Media m) {
        if (m != null) {
            mediaList.add(m);
        }
    }

    /**
     * Returns all media items currently in the system.
     *
     * @return list of media items
     */
    public List<Media> getAllMedia() {
        return mediaList;
    }

    /**
     * Borrows a media item by title for the given user using today's date.
     *
     * @param username the username of the borrower
     * @param title    the title of the media item
     * @return a status message describing the result
     */
    public String borrowMedia(String username, String title) {
        return borrowMedia(username, title, LocalDate.now());
    }

    /**
     * Borrows a media item by title for the given user and date (testable).
     *
     * @param username the username of the borrower
     * @param title    the title of the media item
     * @param today    the current date (for testing or real use)
     * @return a status message describing the result
     */
    public String borrowMedia(String username, String title, LocalDate today) {

        if (username == null || username.trim().isEmpty() ||
            title == null || title.trim().isEmpty()) {
            return "Username and title cannot be empty!";
        }

        for (Media m : mediaList) {
            if (m.getTitle().equalsIgnoreCase(title)) {

                if (m.isBorrowed()) {
                    return "Media is already borrowed!";
                }

                LocalDate due = today.plusDays(m.getBorrowDays());
                m.borrow(username, due);
                return "Media borrowed successfully! Due date: " + due;
            }
        }

        return "Media not found!";
    }
}
