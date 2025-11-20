package service;

import domain.Admin;
import domain.Session;
import presentation.JsonAdminRepository;

public class AdminService {

    private final JsonAdminRepository repo;
    private final Session session;

    public AdminService(JsonAdminRepository repo, Session session) {
        this.repo = repo;
        this.session = session;
    }

    /**
     * Attempts to log in the administrator.
     * Covers all validation cases.
     */
    public String login(String username, String password) {

        // Case 1: Already logged in
        if (session.isLoggedIn()) {
            return "Already logged in!";
        }

        // Case 2: Empty or null input
        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            return "Username or password cannot be empty!";
        }

        // Case 3: Check repository
        Admin admin = repo.findAdmin(username, password);
        if (admin == null) {
            return "Invalid username or password!";
        }

        // Case 4: Success
        session.login(username);
        return "Login successful!";
    }


    /**
     * Logs out the admin. Must be logged in already.
     */
    public String logout() {
        if (!session.isLoggedIn()) {
            return "No admin is logged in!";
        }

        session.logout();
        return "Logout successful!";
    }

    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }
}
