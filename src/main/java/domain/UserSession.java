package domain;

/**
 * Represents a session for a normal library user.
 * <p>
 * This class tracks whether a user is logged in and stores the username
 * of the currently authenticated user.
 * </p>
 *
 * @version 1.0
 * author Dana
 */
public class UserSession {

    /** Indicates whether a user is currently logged in. */
    private boolean loggedIn = false;

    /** The username of the logged-in user. */
    private String username;

    /**
     * Checks if a user is currently logged in.
     *
     * @return {@code true} if logged in, otherwise {@code false}
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Returns the username of the currently logged-in user.
     *
     * @return the username, or {@code null} if no user is logged in
     */
    public String getLoggedUser() {
        return username;
    }

    /**
     * Logs in a user by setting the session state and storing the username.
     *
     * @param username the username of the user logging in
     */
    public void login(String username) {
        this.loggedIn = true;
        this.username = username;
    }

    /**
     * Logs out the current user and clears the session data.
     */
    public void logout() {
        this.loggedIn = false;
        this.username = null;
    }
}
