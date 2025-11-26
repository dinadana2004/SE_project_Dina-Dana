package service;

import domain.Admin;
import domain.Session;
import presentation.JsonAdminRepository;

/**
 * Service class responsible for managing admin-related operations such as login and logout.
 * It interacts with {@link JsonAdminRepository} to validate admin credentials and uses
 * {@link Session} to maintain login state.
 *
 * @author Dana
 * @version 1.0
 */
public class AdminService {

    /** Repository used to load and validate admin accounts. */
    private final JsonAdminRepository repo;

    /** Session object used to track admin login status. */
    private final Session session;

    /**
     * Constructs an AdminService with the given repository and session manager.
     *
     * @param repo    the repository used to retrieve admin data
     * @param session the session object managing admin login state
     */
    public AdminService(JsonAdminRepository repo, Session session) {
        this.repo = repo;
        this.session = session;
    }

    /**
     * Attempts to log in an admin using a username and password.
     *
     * @param username the admin's username
     * @param password the admin's password
     * @return a message indicating whether login was successful or why it failed
     */
    public String login(String username, String password) {

        if (session.isLoggedIn())
            return "Already logged in!";

        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            return "Username or password cannot be empty!";
        }

        Admin admin = repo.findAdmin(username, password);
        if (admin == null) return "Invalid username or password!";

        session.login(username);
        return "Login successful!";
    }

    /**
     * Logs out the currently logged-in admin.
     *
     * @return a message indicating the result of the logout operation
     */
    public String logout() {
        if (!session.isLoggedIn()) return "No admin is logged in!";
        session.logout();
        return "Logout successful!";
    }

    /**
     * Checks whether an admin is currently logged in.
     *
     * @return {@code true} if an admin is logged in, {@code false} otherwise
     */
    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }
}
