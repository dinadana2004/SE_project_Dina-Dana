package presentation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import domain.Admin;

import java.io.FileReader;
import java.util.List;

public class JsonAdminRepository {

    private static final String FILE = "src/main/resources/admins.json";
    private List<Admin> admins;

    public JsonAdminRepository() {
        try {
            admins = new Gson().fromJson(new FileReader(FILE),
                    new TypeToken<List<Admin>>(){}.getType());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load admins.json");
        }
    }

    public Admin findAdmin(String username, String password) {
        return admins.stream()
                .filter(a -> a.getUsername().equals(username) &&
                             a.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
