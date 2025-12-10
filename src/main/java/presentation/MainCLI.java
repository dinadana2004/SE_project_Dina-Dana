package presentation;

import domain.Book;
import domain.CD;
import domain.OverdueItem;
import domain.Session;
import domain.UserSession;
import service.AdminService;
import service.BookService;
import service.UserService;
import service.LoanService;
import service.MediaService;
import service.OverdueReportService;
import service.EmailNotifier;
import service.ReminderService;
import service.EmailSender;
import service.BorrowUnderRestrictionds;   
import service.UnregisterUser;            

import java.util.List;
import java.util.Scanner;

/**
 * The main entry point for the Library Management System.
 * Commented out during testing to prevent console execution.
 */
public class MainCLI {

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

        // Sprint 4 services (ADDED ONLY)
        BorrowUnderRestrictionds borrowRestrictions =
                new BorrowUnderRestrictionds(userRepo, bookRepo, adminService, loanService);

        UnregisterUser unregisterUser =
                new UnregisterUser(userRepo, bookRepo, adminService, loanService);

        // Media (Sprint 5)
        MediaService mediaService = new MediaService();
        OverdueReportService mediaReportService = new OverdueReportService(mediaService.getAllMedia());

        // Email (Sprint 3)
        String senderEmail = "dina.hanna2004@gmail.com";
        String appPassword = "YOUR_GMAIL_APP_PASSWORD";

        EmailSender emailSender = new EmailNotifier(senderEmail, appPassword);
        ReminderService reminderService = new ReminderService(loanService, userRepo, emailSender);

        while (true) {

            System.out.println("\n====== Library Management System ======");
            System.out.println("1. Admin Menu");
            System.out.println("2. User Menu");
            System.out.println("3. Media Menu");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    adminMenu(sc, adminService, bookService, reminderService, unregisterUser);
                    break;

                case 2:
                    userMenu(sc, userService, bookService, loanService, borrowRestrictions);
                    break;

                case 3:
                    mediaMenu(sc, mediaService, userService, mediaReportService);
                    break;

                case 4:
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // ============================
    // ADMIN MENU
    // ============================

    private static void adminMenu(Scanner sc,
                                  AdminService adminService,
                                  BookService bookService,
                                  ReminderService reminderService,
                                  UnregisterUser unregisterUser) {

        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Login");
            System.out.println("2. Logout");
            System.out.println("3. Add Book");
            System.out.println("4. Send Overdue Reminders");
            System.out.println("5. Unregister User"); // Sprint 4
            System.out.println("6. Back");

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
                    if (!adminService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }
                    System.out.println("Sending reminders...");
                    int sent = reminderService.sendReminders();
                    System.out.println("Emails sent: " + sent);
                    break;

                case 5:
                    if (!adminService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }
                    System.out.print("Enter username to unregister: ");
                    String delUser = sc.nextLine();
                    System.out.println(unregisterUser.unregisterUser(delUser));
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // ============================
    // USER MENU
    // ============================

    private static void userMenu(Scanner sc,
                                 UserService userService,
                                 BookService bookService,
                                 LoanService loanService,
                                 BorrowUnderRestrictionds borrowRestrictions) {

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
                    if (!userService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }
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

                    // Sprint 4 (Borrow Restrictions)
                    System.out.println(borrowRestrictions.borrowRestrictions(username, isbn));
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

    // ============================
    // MEDIA MENU — Sprint 5
    // ============================

    private static void mediaMenu(Scanner sc, MediaService mediaService,
                                  UserService userService,
                                  OverdueReportService mediaReportService) {

        while (true) {
            System.out.println("\n--- Media Menu ---");
            System.out.println("1. Add CD");
            System.out.println("2. Borrow CD");
            System.out.println("3. View Mixed Overdue Report");
            System.out.println("4. Back");

            System.out.print("Choose option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("CD Title: ");
                    String title = sc.nextLine();

                    System.out.print("Artist: ");
                    String artist = sc.nextLine();

                    mediaService.addMedia(new CD(title, artist));
                    System.out.println("CD added successfully!");
                    break;

                case 2:
                    if (!userService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }

                    System.out.print("Enter CD title: ");
                    String cdTitle = sc.nextLine();

                    String username = userService.getLoggedUser().getUsername();
                    System.out.println(mediaService.borrowMedia(username, cdTitle));
                    break;

                case 3:
                    List<OverdueItem> report =
                            mediaReportService.generateReport(java.time.LocalDate.now());

                    if (report.isEmpty()) {
                        System.out.println("No overdue media items.");
                    } else {
                        int total = mediaReportService.calculateTotalFine(report);
                        System.out.println("=== Overdue Media Report ===");

                        for (OverdueItem item : report) {
                            System.out.println(
                                    item.getMedia().getMediaType() + " | " +
                                    item.getMedia().getTitle() + " | Fine: " + item.getFineAmount() + " NIS"
                            );
                        }

                        System.out.println("Total Fine: " + total + " NIS");
                    }
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}