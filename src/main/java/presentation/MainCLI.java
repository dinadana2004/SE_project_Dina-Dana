package presentation;

import domain.Book;
import domain.Session;
import presentation.JsonAdminRepository;
import presentation.JsonBookRepository;
import service.AdminService;
import service.BookService;

import java.util.List;
import java.util.Scanner;

public class MainCLI {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // REAL repositories (JSON)
        JsonAdminRepository adminRepo = new JsonAdminRepository();
        JsonBookRepository bookRepo = new JsonBookRepository();

        // Services
        Session session = new Session();
        AdminService adminService = new AdminService(adminRepo, session);
        BookService bookService = new BookService(bookRepo);

        while (true) {

            System.out.println("\n====== Library Management System ======");
            System.out.println("1. Admin Login");
            System.out.println("2. Admin Logout");
            System.out.println("3. Add Book");
            System.out.println("4. Search Book");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1: // LOGIN
                    System.out.print("Enter username: ");
                    String user = sc.nextLine();

                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();

                    System.out.println(adminService.login(user, pass));
                    break;

                case 2: // LOGOUT
                    System.out.println(adminService.logout());
                    break;

                case 3: // ADD BOOK
                    if (!adminService.isLoggedIn()) {
                        System.out.println("You must login first!");
                        break;
                    }

                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter book author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter book ISBN: ");
                    String isbn = sc.nextLine();

                    Book newBook = new Book(title, author, isbn);
                    System.out.println(bookService.addBook(newBook));
                    break;

                case 4: // SEARCH
                    System.out.print("Enter keyword (title/author/ISBN): ");
                    String keyword = sc.nextLine();

                    List<Book> results = bookService.search(keyword);

                    if (results.isEmpty()) {
                        System.out.println("No matching books found.");
                    } else {
                        System.out.println("\n--- Search Results ---");
                        for (Book b : results) {
                            System.out.println("Title: " + b.getTitle());
                            System.out.println("Author: " + b.getAuthor());
                            System.out.println("ISBN: " + b.getIsbn());
                            System.out.println("----------------------------");
                        }
                    }
                    break;

                case 5: // EXIT
                    System.out.println("Goodbye!");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
