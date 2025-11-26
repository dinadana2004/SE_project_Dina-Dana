package presentation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing {@link User} data stored in a JSON file.
 * Provides functionality to load, save, update, and search for users.
 *
 * <p>Data is persisted using Gson for JSON serialization and deserialization.</p>
 * 
 * @author Dana
 * @version 1.0
 */
public class JsonUserRepository {

    /** Path to the JSON file that stores all registered users. */
    private static final String FILE = "src/main/resources/users.json";

    /** Internal list holding all user objects loaded from storage. */
    private List<User> users;

    /**
     * Constructs a JsonUserRepository and loads user data from the JSON file.
     * If the file does not exist or cannot be read, an empty list is created.
     */
    public JsonUserRepository() {
        try {
            users = new Gson().fromJson(
                    new FileReader(FILE),
                    new TypeToken<List<User>>(){}.getType()
            );
            if (users == null) users = new ArrayList<>();
        } catch (Exception e) {
            users = new ArrayList<>();
        }
    }

    /**
     * Searches for a user based on their username.
     *
     * @param username the username to search for (case-insensitive)
     * @return the matching {@link User}, or {@code null} if no match is found
     */
    public User findUser(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a new user to the repository and writes the updated list to the JSON file.
     *
     * @param user the {@link User} object to be saved
     */
    public void save(User user) {
        users.add(user);
        write();
    }

    /**
     * Retrieves all users stored in the repository.
     *
     * @return a list of {@link User} objects
     */
    public List<User> findAll() {
        return users;
    }

    /**
     * Rewrites the JSON file with the current list of users.
     * Useful after modifying user details.
     */
    public void update() {
        write();
    }

    /**
     * Writes the list of users to the JSON file.
     *
     * @throws RuntimeException if writing to the file fails
     */
    private void write() {
        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(new Gson().toJson(users));
        } catch (Exception e) {
            throw new RuntimeException("Error writing users.json");
        }
    }
}
