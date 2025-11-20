package domain;

/**
 * Represents admin session (login/logout).
 */
public class Session {
    private boolean loggedIn = false;
    private String adminUser;

    public boolean isLoggedIn() { return loggedIn; }

    public void login(String username) {
        this.loggedIn = true;
        this.adminUser = username;
    }

    public void logout() {
        this.loggedIn = false;
        this.adminUser = null;
    }
}
