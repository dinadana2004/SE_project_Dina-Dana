package domain;

/**
 * Represents an administrator of the library system.
 * <p>
 * An admin has a username and password and is responsible for logging into the
 * admin panel to perform administrative tasks such as adding books.
 * </p>
 * 
 * @author Dana
 * @version 1.0
 */
public class Admin {

    /** The admin's username. */
    private String username;

    /** The admin's password. */
    private String password;

    /**
     * Default constructor required for JSON deserialization.
     */
    public Admin() {}

    /**
     * Creates a new Admin with the specified username and password.
     *
     * @param username the admin's username
     * @param password the admin's password
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the admin's username.
     *
     * @return the username
     */
    public String getUsername() { return username; }

    /**
     * Returns the admin's password.
     *
     * @return the password
     */
    public String getPassword() { return password; }
}
