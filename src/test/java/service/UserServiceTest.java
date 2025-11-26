package service;

import domain.User;
import domain.UserSession;
import org.junit.jupiter.api.Test;
import presentation.JsonUserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    // ==========================
    //   Mock User Repo
    // ==========================
    private JsonUserRepository mockUserRepo(List<User> initial) {
        return new JsonUserRepository() {

            private List<User> users = new ArrayList<>(initial);

            @Override
            public User findUser(String username) {
                return users.stream()
                        .filter(u -> u.getUsername().equalsIgnoreCase(username))
                        .findFirst().orElse(null);
            }

            @Override
            public void save(User user) {
                users.add(user);
            }

            @Override
            public List<User> findAll() {
                return users;
            }

            @Override
            public void update() {}
        };
    }

    // ==========================
    //   REGISTER TESTS
    // ==========================

    @Test
    void testRegisterSuccess() {
        JsonUserRepository repo = mockUserRepo(new ArrayList<>());
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        String msg = service.registerUser("dana", "1234", "dana@mail.com");
        assertEquals("User registered successfully!", msg);
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void testRegisterEmptyFields() {
        JsonUserRepository repo = mockUserRepo(new ArrayList<>());
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("All fields (username, password, email) are required!",
                service.registerUser("", "1234", "a@a.com"));

        assertEquals("All fields (username, password, email) are required!",
                service.registerUser("dana", "", "a@a.com"));

        assertEquals("All fields (username, password, email) are required!",
                service.registerUser("dana", "1234", ""));
    }

    @Test
    void testRegisterDuplicateUsername() {
        List<User> existing = List.of(new User("dana", "1234", "d@mail.com"));
        JsonUserRepository repo = mockUserRepo(existing);
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("Username already exists!",
                service.registerUser("dana", "9999", "test@mail.com"));
    }

    @Test
    void testRegisterDuplicateEmail() {
        List<User> users = List.of(new User("sara", "1111", "sara@mail.com"));
        JsonUserRepository repo = mockUserRepo(users);
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("Email already registered!",
                service.registerUser("dana", "1234", "sara@mail.com"));
    }

    // ==========================
    //   LOGIN TESTS
    // ==========================

    @Test
    void testLoginSuccess() {
        JsonUserRepository repo = mockUserRepo(List.of(new User("dana", "1234", "d@mail.com")));
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("User login successful!", service.login("dana", "1234"));
        assertTrue(session.isLoggedIn());
    }

    @Test
    void testLoginWrongPassword() {
        JsonUserRepository repo = mockUserRepo(List.of(new User("dana", "1234", "d@mail.com")));
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("Invalid password!", service.login("dana", "5555"));
    }

    @Test
    void testLoginUserNotFound() {
        JsonUserRepository repo = mockUserRepo(new ArrayList<>());
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("User does not exist!", service.login("dana", "1234"));
    }

    @Test
    void testLoginEmptyInputs() {
        JsonUserRepository repo = mockUserRepo(new ArrayList<>());
        UserSession session = new UserSession();
        UserService service = new UserService(repo, session);

        assertEquals("Username or password cannot be empty!", service.login("", "1234"));
        assertEquals("Username or password cannot be empty!", service.login("dana", ""));
    }

    @Test
    void testLoginAlreadyLoggedIn() {
        JsonUserRepository repo = mockUserRepo(List.of(new User("dana", "1234", "d@mail.com")));
        UserSession session = new UserSession();
        session.login("dana");

        UserService service = new UserService(repo, session);

        assertEquals("A user is already logged in!", service.login("dana", "1234"));
    }

    // ==========================
    //   LOGOUT TESTS
    // ==========================

    @Test
    void testLogoutSuccess() {
        JsonUserRepository repo = mockUserRepo(new ArrayList<>());
        UserSession session = new UserSession();
        session.login("dana");

        UserService service = new UserService(repo, session);

        assertEquals("User logged out successfully!", service.logout());
        assertFalse(session.isLoggedIn());
    }

    @Test
    void testLogoutWithoutLogin() {
        JsonUserRepository repo = mockUserRepo(new ArrayList<>());
        UserSession session = new UserSession();

        UserService service = new UserService(repo, session);

        assertEquals("No user is logged in!", service.logout());
    }
}
