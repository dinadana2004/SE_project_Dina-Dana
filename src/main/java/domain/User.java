package domain;

/**
 * Represents a library user with login credentials, email, and fine balance.
 * <p>
 * A user can borrow books, accumulate fines, and make fine payments.  
 * This class stores all relevant user information required by the system.
 * </p>
 *
 * @version 1.0
 * author Dana
 */
public class User {

    /** The username of the user. */
    private String username;

    /** The user's password. */
    private String password;

    /** The user's email address. */
    private String email;

    /** The total fine balance the user owes. */
    private int fineBalance;

    /**
     * Default constructor required for JSON deserialization.
     */
    public User() {}

    /**
     * Creates a new user with zero initial fine balance.
     *
     * @param username the user's username
     * @param password the user's password
     * @param email    the user's email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fineBalance = 0;
    }

    /**
     * Creates a user with a specified fine balance.
     *
     * @param username    the user's username
     * @param password    the user's password
     * @param email       the user's email
     * @param fineBalance the user's existing fine balance
     */
    public User(String username, String password, String email, int fineBalance) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fineBalance = fineBalance;
    }

    /** @return the username */
    public String getUsername() { return username; }

    /** @return the password */
    public String getPassword() { return password; }

    /** @return the email */
    public String getEmail() { return email; }

    /** @return the user's current fine balance */
    public int getFineBalance() { return fineBalance; }

    /**
     * Checks whether the user has any unpaid fines.
     *
     * @return {@code true} if fine balance is greater than zero
     */
    public boolean hasUnpaidFines() {
        return fineBalance > 0;
    }

    /**
     * Adds a fine amount to the user's fine balance.
     *
     * @param amount the fine amount to add
     */
    public void addFine(int amount) {
        this.fineBalance += amount;
    }

    /**
     * Pays a specified fine amount. Fine balance will never go below zero.
     *
     * @param amount the amount paid
     */
    public void payFine(int amount) {
        this.fineBalance -= amount;
        if (fineBalance < 0) {
            fineBalance = 0;
        }
    }
}
