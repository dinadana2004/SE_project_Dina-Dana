package domain;

/**
 * Represents the session state for an administrator, handling login and logout actions.
 * <p>
 * This class tracks whether an admin is logged in and stores the username of
 * the currently logged-in admin.
 * </p>
 * 
 * @version 1.0
 * author Dana
 */
public class Session {

    /** Indicates whether an admin is currently logged in. */
    private boolean loggedIn = false;

    /** The username of the admin currently logged in. */
    private String adminUser;

    /**
     * Checks whether an admin is currently logged in.
     *
     * @return {@code true} if logged in, otherwise {@code false}
     */
    public boolean isLoggedIn() { return loggedIn; }

    /**
     * Logs in an admin by setting the session state and storing the username.
     *
     * @param username the username of the admin logging in
     */
    public void login(String username) {
        this.loggedIn = true;
        this.adminUser = username;
    }

    /**
     * Logs out the current admin and clears all session data.
     */
    public void logout() {
        this.loggedIn = false;
        this.adminUser = null;
    }
}
