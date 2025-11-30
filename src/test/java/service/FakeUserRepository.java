package service;

import domain.User;
import java.util.ArrayList;
import java.util.List;

public class FakeUserRepository extends presentation.JsonUserRepository {

    public List<User> usersList = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return usersList;
    }

    @Override
    public void save(User user) {
        usersList.add(user);
    }

    @Override
    public User findUser(String username) {
        return usersList.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }
}
