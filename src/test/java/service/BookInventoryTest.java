package service;

import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

class ObserverPatternBookTest {

    @Test
    void testObserverIsNotified() {

        // Arrange
        BookInventory inventory = new BookInventory();
        TestUserObserver observer = new TestUserObserver();

        inventory.addObserver(observer);

        // Act
        inventory.bookReturned("Clean Code");

        // Assert
        assertTrue(observer.wasNotified, "Observer should have been notified");
        assertEquals("Clean Code", observer.lastBookTitle);
    }

    // A simple fake observer for testing
    static class TestUserObserver implements Observer {

        boolean wasNotified = false;
        String lastBookTitle = null;

        @Override
        public void update(Observable o, Object arg) {
            wasNotified = true;
            lastBookTitle = (String) arg;
        }
    }
}