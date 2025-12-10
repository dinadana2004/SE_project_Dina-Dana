package service;

import domain.User;
import domain.UserSession;
import presentation.JsonUserRepository;

/**
 * Service class responsible for managing users: registration, login and logout.
 *
 * This version includes refactoring to reduce duplication, improve readability,
 * and lower cognitive complexity (important for SonarCloud).
 *
 * @author Dana
 * @version 2.0 (Refactored)
 */
public class UserService {

    private final JsonUserRepository repo;
    private final UserSession session;

    public UserService(JsonUserRepository repo, UserSession session) {
        this.repo = repo;
        this.session = session;
    }

   

    public String registerUser(String username, String password, String email) {

        String validationError = validateRegisterInput(username, password, email);
        if (validationError != null)
            return validationError;

        if (repo.findUser(username) != null)
            return "Username already exists!";

        boolean emailExists = repo.findAll().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));

        if (emailExists)
            return "Email already registered!";

        repo.save(new User(username, password, email));
        return "User registered successfully!";
    }


    public String login(String username, String password) {

        if (session.isLoggedIn())
            return "A user is already logged in!";

        String validationError = validateLoginInput(username, password);
        if (validationError != null)
            return validationError;

        User user = repo.findUser(username);
        if (user == null)
            return "User does not exist!";

        if (!user.getPassword().equals(password))
            return "Invalid password!";

        session.login(username);
        return "User login successful!";
    }


    public String logout() {
        if (!session.isLoggedIn())
            return "No user is logged in!";

        session.logout();
        return "User logged out successfully!";
    }


    public User getLoggedUser() {
        return session.isLoggedIn()
                ? repo.findUser(session.getLoggedUser())
                : null;
    }

    public boolean isLoggedIn() {
        return session.isLoggedIn();
    }


    
    private String validateRegisterInput(String username, String password, String email) {
        if (isNullOrEmpty(username) || isNullOrEmpty(password) || isNullOrEmpty(email))
            return "All fields (username, password, email) are required!";
        return null;
    }

    private String validateLoginInput(String username, String password) {
        if (isNullOrEmpty(username) || isNullOrEmpty(password))
            return "Username or password cannot be empty!";
        return null;
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}