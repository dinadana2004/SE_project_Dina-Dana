package presentation;

import domain.Book;
import service.BookService;
import java.util.List;
import java.util.Scanner;

/**
 * Presentation layer class that provides a simple console interface
 * for adding and searching books in the library.
 */
public class BookPresentation {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        BookService service = new BookService(null); 

        while (true) {
            System.out.println("\n=== Library Book Services ===");
            System.out.println("1. Add New Book");
            System.out.println("2. Search for a Book");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine(); // Clear buffer after nextInt()

            switch (choice) {
                case 1:
                    System.out.println("\n=== Add a New Book ===");
                    System.out.print("Enter title: ");
                    String title = input.nextLine();

                    System.out.print("Enter author: ");
                    String author = input.nextLine();

                    System.out.print("Enter ISBN: ");
                    String isbn = input.nextLine();

                    Book newBook = new Book(title, author, isbn);
                    String result = service.add_NewBook(newBook);
                    System.out.println(result);
                    break;

                case 2:
                    System.out.println("\n=== Search for a Book ===");
                    System.out.println("1. Search by Title");
                    System.out.println("2. Search by Author");
                    System.out.println("3. Search by ISBN");
                    System.out.print("Enter your choice: ");
                    int searchChoice = input.nextInt();
                    input.nextLine(); // Clear buffer

                    switch (searchChoice) {
                        case 1:
                            System.out.print("Enter title: ");
                            String enteredTitle = input.nextLine();
                            List<Book> titleResults = service.search_book_title(enteredTitle);
                            displayResults(titleResults);
                            break;

                        case 2:
                            System.out.print("Enter author: ");
                            String enteredAuthor = input.nextLine();
                            List<Book> authorResults = service.search_book_author(enteredAuthor);
                            displayResults(authorResults);
                            break;

                        case 3:
                            System.out.print("Enter ISBN: ");
                            String enteredIsbn = input.nextLine();
                            Book foundBook = service.search_book_isbn(enteredIsbn);
                            if (foundBook != null) {
                                System.out.println(foundBook);
                            } else {
                                System.out.println("No book found with this ISBN.");
                            }
                            break;

                        default:
                            System.out.println("Invalid option!");
                            break;
                    }
                    break;

                case 3:
                    System.out.println("Exiting... Goodbye!");
                    input.close();
                    return;

                default:
                    System.out.println("Invalid option! Please try again.");
                    break;
            }
        }
    }

    /**
     * Displays a list of books in a readable format.
     *
     * @param books the list of books to display
     */
    private static void displayResults(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }
}
		 
			 


