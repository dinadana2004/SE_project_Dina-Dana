package service;

import domain.Admin;
import domain.Session;
import presentation.JsonAdminRepository;

/**
 * Service class responsible for managing admin-related operations such as login and logout.
 * It interacts with {@link JsonAdminRepository} to validate admin credentials and uses
 * {@link Session} to maintain login state.
 */
public class AdminService {

    private final JsonAdminRepository repo;
    private final Session session;

    public AdminService(JsonAdminRepository repo, Session session) {
        this.repo = repo;
        this.session = session;
    }

    /**
     * Attempts to log in an admin using provided credentials.
     *
     * @param username admin username
     * @param password admin password
     * @return result message
     */
    public String login(String username, String password) {

        if (session.isLoggedIn())  
            return "Already logged in!";

        if (isNullOrEmpty(username) || isNullOrEmpty(password))
            return "Username or password cannot be empty!";

        Admin admin = repo.findAdmin(username, password);

        if (admin == null)
            return "Invalid username or password!";

        session.login(username);
        return "Login successful!";
    }

    /**
     * Logs out the currently logged-in admin.
     */
    public String logout() {
        if (!session.isLoggedIn())
            return "No admin is logged in!";

        session.logout();
        return "Logout successful!";
    }

    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }

    /**
     * Utility method to check if a string is null or empty after trimming.
     */
    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}