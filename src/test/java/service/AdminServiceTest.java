package service;

import domain.Admin;
import domain.Session;
import org.junit.jupiter.api.Test;
import presentation.JsonAdminRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    // Mock Admin Repo
    private JsonAdminRepository mockRepo(List<Admin> admins) {
        return new JsonAdminRepository() {

            private List<Admin> data = admins;

            @Override
            public Admin findAdmin(String username, String password) {
                return data.stream()
                        .filter(a -> a.getUsername().equalsIgnoreCase(username)
                                && a.getPassword().equals(password))
                        .findFirst().orElse(null);
            }

            @Override
            public Admin findAdmin(String username) {
                return data.stream()
                        .filter(a -> a.getUsername().equalsIgnoreCase(username))
                        .findFirst().orElse(null);
            }
        };
    }

    @Test
    void testLoginSuccess() {
        JsonAdminRepository repo = mockRepo(List.of(new Admin("admin", "1234")));
        Session session = new Session();
        AdminService service = new AdminService(repo, session);

        assertEquals("Login successful!", service.login("admin", "1234"));
        assertTrue(session.isLoggedIn());
    }

    @Test
    void testLoginWrongPassword() {
        JsonAdminRepository repo = mockRepo(List.of(new Admin("admin", "1234")));
        Session session = new Session();
        AdminService service = new AdminService(repo, session);

        assertEquals("Invalid username or password!", service.login("admin", "9999"));
    }

    @Test
    void testLoginUserNotFound() {
        JsonAdminRepository repo = mockRepo(new ArrayList<>());
        Session session = new Session();
        AdminService service = new AdminService(repo, session);

        assertEquals("Invalid username or password!", service.login("admin", "1234"));
    }

    @Test
    void testLoginEmptyInputs() {
        JsonAdminRepository repo = mockRepo(new ArrayList<>());
        Session session = new Session();
        AdminService service = new AdminService(repo, session);

        assertEquals("Username or password cannot be empty!", service.login("", "1234"));
        assertEquals("Username or password cannot be empty!", service.login("admin", ""));
    }

    @Test
    void testLoginAlreadyLoggedIn() {
        JsonAdminRepository repo = mockRepo(List.of(new Admin("admin", "1234")));
        Session session = new Session();
        session.login("admin");

        AdminService service = new AdminService(repo, session);

        assertEquals("Already logged in!", service.login("admin", "1234"));
    }

    @Test
    void testLogoutSuccess() {
        JsonAdminRepository repo = mockRepo(List.of(new Admin("admin", "1234")));
        Session session = new Session();
        session.login("admin");

        AdminService service = new AdminService(repo, session);

        assertEquals("Logout successful!", service.logout());
        assertFalse(session.isLoggedIn());
    }

    @Test
    void testLogoutWithoutLogin() {
        JsonAdminRepository repo = mockRepo(List.of(new Admin("admin", "1234")));
        Session session = new Session();

        AdminService service = new AdminService(repo, session);

        assertEquals("No admin is logged in!", service.logout());
    }
}
