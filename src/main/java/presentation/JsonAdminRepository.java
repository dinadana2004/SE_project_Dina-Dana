package presentation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Admin;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing Admin data stored in a JSON file.
 * It loads admin accounts on initialization and provides methods for
 * searching and saving Admin objects.
 *
 * <p>This class uses Gson to serialize and deserialize admin data.</p>
 *
 * @author Dana
 * @version 1.0
 */
public class JsonAdminRepository {

    /** The JSON file path where admin data is stored. */
    private static final String FILE = "src/main/resources/admins.json";

    /** Internal list containing all stored admins. */
    private List<Admin> admins;

    /**
     * Constructs a JsonAdminRepository and loads admin data from the JSON file.
     * If the file does not exist or cannot be read, an empty admin list is created.
     */
    public JsonAdminRepository() {
        try {
            admins = new Gson().fromJson(
                    new FileReader(FILE),
                    new TypeToken<List<Admin>>(){}.getType()
            );
            if (admins == null) admins = new ArrayList<>();
        } catch (Exception e) {
            admins = new ArrayList<>();
        }
    }

    /**
     * Searches for an admin using both username and password.
     *
     * @param username the admin's username (case insensitive)
     * @param password the admin's password
     * @return the matching Admin object, or {@code null} if no match is found
     */
    public Admin findAdmin(String username, String password) {
        return admins.stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username)
                        && a.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    /**
     * Searches for an admin using only the username.
     *
     * @param username the admin's username (case insensitive)
     * @return the matching Admin, or {@code null} if none exists
     */
    public Admin findAdmin(String username) {
        return admins.stream()
                .filter(a -> a.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves a new admin to the list and writes changes to the JSON file.
     *
     * @param admin the Admin object to be saved
     */
    public void save(Admin admin) {
        admins.add(admin);
        write();
    }

    /**
     * Writes the current admin list to the JSON file.
     *
     * @throws RuntimeException if writing to the file fails
     */
    private void write() {
        try (FileWriter fw = new FileWriter(FILE)) {
            fw.write(new Gson().toJson(admins));
        } catch (Exception e) {
            throw new RuntimeException("Error writing admins.json");
        }
    }
}
