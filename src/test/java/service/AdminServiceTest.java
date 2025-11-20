package service;

import domain.Admin;
import domain.Session;
import presentation.JsonAdminRepository;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    // Fake Admin Repository for testing (no JSON needed)
    private JsonAdminRepository mockRepo(List<Admin> admins) {
        return new JsonAdminRepository() {
            @Override
            public Admin findAdmin(String username, String password) {
                return admins.stream()
                        .filter(a -> a.getUsername().equals(username)
                                && a.getPassword().equals(password))
                        .findFirst()
                        .orElse(null);
            }
        };
    }

    @Test
    void testLoginSuccess() {
        var repo = mockRepo(List.of(new Admin("admin", "1234")));
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("Login successful!", service.login("admin", "1234"));
        assertTrue(service.isLoggedIn());
    }

    @Test
    void testLoginInvalidCredentials() {
        var repo = mockRepo(Collections.emptyList());
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("Invalid username or password!", service.login("admin", "9999"));
        assertFalse(session.isLoggedIn());
    }

    @Test
    void testLoginEmptyUsername() {
        var repo = mockRepo(Collections.emptyList());
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("Username or password cannot be empty!", service.login("", "1234"));
    }

    @Test
    void testLoginEmptyPassword() {
        var repo = mockRepo(Collections.emptyList());
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("Username or password cannot be empty!", service.login("admin", ""));
    }

    @Test
    void testLoginNullUsername() {
        var repo = mockRepo(Collections.emptyList());
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("Username or password cannot be empty!", service.login(null, "1234"));
    }

    @Test
    void testLoginNullPassword() {
        var repo = mockRepo(Collections.emptyList());
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("Username or password cannot be empty!", service.login("admin", null));
    }

    @Test
    void testLoginWhileAlreadyLoggedIn() {
        var repo = mockRepo(List.of(new Admin("admin", "1234")));
        var session = new Session();
        session.login("admin");

        var service = new AdminService(repo, session);

        assertEquals("Already logged in!", service.login("admin", "1234"));
    }

    @Test
    void testLogoutSuccess() {
        var repo = mockRepo(List.of(new Admin("admin", "1234")));
        var session = new Session();
        session.login("admin");

        var service = new AdminService(repo, session);

        assertEquals("Logout successful!", service.logout());
        assertFalse(session.isLoggedIn());
    }

    @Test
    void testLogoutWithoutLogin() {
        var repo = mockRepo(List.of(new Admin("admin", "1234")));
        var session = new Session();
        var service = new AdminService(repo, session);

        assertEquals("No admin is logged in!", service.logout());
    }
}
