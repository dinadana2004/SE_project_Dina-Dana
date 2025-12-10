package service;

import domain.User;

import presentation.JsonBookRepository;
import presentation.JsonUserRepository;

import java.util.List;
import java.util.Objects;

/**
 * Service responsible for unregistering inactive users.
 * <p>
 * Rules:
 * <ul>
 *     <li>Only logged-in admins can unregister users.</li>
 *     <li>User must exist.</li>
 *     <li>User must not have unpaid fines.</li>
 *     <li>User must not have active borrowed books.</li>
 * </ul>
 */
public class UnregisterUser {

    private final JsonUserRepository userRepo;
    private final JsonBookRepository bookRepo;
    private final AdminService adminService;
    private final LoanService loanService;

    public UnregisterUser(JsonUserRepository userRepo,
                          JsonBookRepository bookRepo,
                          AdminService adminService,
                          LoanService loanService) {

        this.userRepo = Objects.requireNonNull(userRepo);
        this.bookRepo = Objects.requireNonNull(bookRepo);
        this.adminService = Objects.requireNonNull(adminService);
        this.loanService = Objects.requireNonNull(loanService);
    }

    /**
     * Unregisters a user if allowed by the rules.
     *
     * @param username username of the user to unregister
     * @return message describing the outcome
     */
    public String unregisterUser(String username) {

        if (!adminService.isLoggedIn())
            return "Only admins can unregister users.";

        if (username == null || username.trim().isEmpty()) {
            return "username shouldn't be empty";
        }

        User user = userRepo.findUser(username);
        if (user == null) {
            return "this user does not exist";
        }

        if (user.hasUnpaidFines())
            return "User has unpaid fines and cannot be unregistered.";

        boolean isActive = bookRepo.findAll().stream()
                .anyMatch(b -> b.isBorrowed()
                        && username.equalsIgnoreCase(b.getBorrowedByUser()));
        if (isActive)
            return "User has active loans and cannot be unregistered.";

        List<User> list = userRepo.findAll();
        list.removeIf(u -> u.getUsername().equalsIgnoreCase(username));
        userRepo.update();

        return "User unregistered successfully.";
    }
}