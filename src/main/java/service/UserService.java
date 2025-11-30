package service;

import domain.User;
import domain.UserSession;
import presentation.JsonUserRepository;

/**
 * Service class responsible for managing user-related operations such as
 * registration, login, logout, and retrieving logged-in user information.
 *
 * <p>This service interacts with {@link JsonUserRepository} for user data
 * persistence and uses {@link UserSession} to maintain login state.</p>
 *
 * @author Dana
 * @version 1.0
 */
public class UserService {

    /** Repository used to store and retrieve user accounts. */
    private JsonUserRepository repo;

    /** Session object used to track the currently logged-in user. */
    private UserSession session;

    /**
     * Constructs a UserService with the provided user repository and session manager.
     *
     * @param repo    the repository that handles users
     * @param session the session object managing login state
     */
    public UserService(JsonUserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

    /**
     * Registers a new user after validating input and ensuring uniqueness
     * of both username and email.
     *
     * @param username the chosen username
     * @param password the user's password
     * @param email    the user's email address
     * @return a message describing whether registration was successful or failed
     */
    public String registerUser(String username, String password, String email) {

        if (username == null || password == null || email == null ||
                username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()) {
            return "All fields (username, password, email) are required!";
        }

        User existing = repo.findUser(username);
        if (existing != null)
            return "Username already exists!";

        for (User u : repo.findAll()) {
            if (u.getEmail().equalsIgnoreCase(email))
                return "Email already registered!";
        }

        repo.save(new User(username, password, email));
        return "User registered successfully!";
    }

    /**
     * Logs in a user by validating the username and password.
     *
     * @param username the input username
     * @param password the input password
     * @return a status message describing the result of the login attempt
     */
    public String login(String username, String password) {

        if (session.isLoggedIn())
            return "A user is already logged in!";

        if (username == null || password == null ||
                username.trim().isEmpty() || password.trim().isEmpty()) {
            return "Username or password cannot be empty!";
        }

        User user = repo.findUser(username);
        if (user == null)
            return "User does not exist!";

        if (!user.getPassword().equals(password))
            return "Invalid password!";

        session.login(username);
        return "User login successful!";
    }

    /**
     * Logs out the currently logged-in user.
     *
     * @return a message describing the logout result
     */
    public String logout() {
        if (!session.isLoggedIn())
            return "No user is logged in!";
        session.logout();
        return "User logged out successfully!";
    }

    /**
     * Retrieves the user object associated with the currently logged-in session.
     *
     * @return the logged-in {@link User}, or {@code null} if no user is logged in
     */
    public User getLoggedUser() {
        if (!session.isLoggedIn())
            return null;
        return repo.findUser(session.getLoggedUser());
    }

    /**
     * Checks if a user is currently logged in.
     *
     * @return {@code true} if logged in, otherwise {@code false}
     */
    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }
    
  
}
