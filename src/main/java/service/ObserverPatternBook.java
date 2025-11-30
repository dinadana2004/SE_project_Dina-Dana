package service;

import java.util.Observable;
import java.util.Observer;


class BookInventory extends Observable {
    public void bookReturned(String bookTitle) {
        System.out.println("Book '" + bookTitle + "' has returned to the inventory.");

        // Mark that the observable's state has changed
        setChanged();

        // Notify all observers (library users) about the returned book
        notifyObservers(bookTitle);
    }
}


class LibraryUser implements Observer {
    private String name;
    private String email;

    public LibraryUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Automatically called when notifyObservers() is triggered
    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof String) {
            String bookTitle = (String) arg;
            System.out.println(name + " (" + email + ") notified: '" + bookTitle + "' is now available!");
        }
    }
}


public class ObserverPatternBook {
    public static void main(String[] args) {
        // Create observable (subject)
        BookInventory inventory = new BookInventory();

        // Create observers (users)
        LibraryUser ali = new LibraryUser("Ali", "ali@gmail.com");
        LibraryUser sami = new LibraryUser("Sami", "sami@gmail.com");

        // Register observers with the inventory
        inventory.addObserver(ali);
        inventory.addObserver(sami);

        // Simulate a book return event
        inventory.bookReturned("Clean Code");
    }
}
