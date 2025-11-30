package presentation;

import domain.Book;
import domain.Session;
import domain.UserSession;
import service.AdminService;
import service.BookService;
import service.UserService;
import service.LoanService;

import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the Library Management System.
 * <p>
 * This class provides a Command-Line Interface (CLI) for interacting with
 * admin and user features such as logging in, adding books, borrowing books,
 * registering users, and paying fines.
 * </p>
 *
 * <p>The application uses JSON-based repositories for persistence and
 * service classes to handle business logic.</p>
 *
 * @author Dana
 * @version 1.0
 */
public class MainCLI {

    /**
     * Starts the Library Management System CLI and displays the main menu.
     *
     * @param args default command-line arguments (unused)
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // REAL repositories (JSON)
        JsonAdminRepository adminRepo = new JsonAdminRepository();
        JsonBookRepository bookRepo = new JsonBookRepository();
        JsonUserRepository userRepo = new JsonUserRepository();

        // Sessions
        Session adminSession = new Session();
        UserSession userSession = new UserSession();

        // Services
        AdminService adminService = new AdminService(adminRepo, adminSession);
        BookService bookService = new BookService(bookRepo);
        UserService userService = new UserService(userRepo, userSession);
        LoanService loanService = new LoanService(bookRepo, userRepo);

        while (true) {

            System.out.println("\n====== Library Management System ======");
            System.out.println("1. Admin Menu");
            System.out.println("2. User Menu");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    adminMenu(sc, adminService, bookService);
                    break;

                case 2:
                    userMenu(sc, userService, bookService, loanService);
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // ============================
    // ADMIN MENU
    // ============================

    /**
     * Displays and handles the Admin menu options such as login, logout,
     * and adding books to the library.
     *
     * @param sc            the Scanner used to read user input
     * @param adminService  service handling admin authentication and actions
     * @param bookService   service used for adding new books
     */
    private static void adminMenu(Scanner sc, AdminService adminService, BookService bookService) {

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. Add Book");
            System.out.println("4. Back");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();
                    System.out.println(adminService.login(u, p));
                    break;

                case 2:
                    System.out.println(adminService.logout());
                    break;

                case 3:
                    if (!adminService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }
                    System.out.print("Title: ");
                    String t = sc.nextLine();
                    System.out.print("Author: ");
                    String a = sc.nextLine();
                    System.out.print("ISBN: ");
                    String i = sc.nextLine();
                    System.out.println(bookService.addBook(new Book(t, a, i)));
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // ============================
    // USER MENU
    // ============================

    /**
     * Displays and handles the User menu options including registration,
     * login, book search, borrowing, and paying fines.
     *
     * @param sc           the Scanner used to read user input
     * @param userService  service managing user authentication and accounts
     * @param bookService  service for searching and retrieving book data
     * @param loanService  service managing borrowing and fine payments
     */
    private static void userMenu(Scanner sc, UserService userService,
                                 BookService bookService, LoanService loanService) {

        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Search Book");
            System.out.println("5. Borrow Book");
            System.out.println("6. Pay Fine");
            System.out.println("7. Back");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();
                    System.out.print("Email: ");
                    String e = sc.nextLine();
                    System.out.println(userService.registerUser(u, p, e));
                    break;

                case 2:
                    System.out.print("Username: ");
                    String lu = sc.nextLine();
                    System.out.print("Password: ");
                    String lp = sc.nextLine();
                    System.out.println(userService.login(lu, lp));
                    break;

                case 3:
                    System.out.println(userService.logout());
                    break;

                case 4:
                    System.out.print("Keyword:(Title/Author/ISBN) ");
                    List<Book> r = bookService.search(sc.nextLine());
                    if (r.isEmpty()) System.out.println("No results.");
                    else r.forEach(b -> {
                        System.out.println(b.getTitle() + " | " + b.getAuthor() + " | " + b.getIsbn());
                    });
                    break;

                case 5:
                    if (!userService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }
                    System.out.print("Enter ISBN to borrow: ");
                    String isbn = sc.nextLine();
                    String username = userService.getLoggedUser().getUsername();
                    System.out.println(loanService.borrowBook(username, isbn));
                    break;

                case 6:
                    if (!userService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }
                    System.out.print("Amount to pay: ");
                    int amt = Integer.parseInt(sc.nextLine());
                    String user = userService.getLoggedUser().getUsername();
                    System.out.println(loanService.payFine(user, amt));
                    break;

                case 7:
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
