package service;
import domain.FineStrategy;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class FineStrategyTest {

    @Test
    void bookFine_isTenNisPerDay() {
        FineStrategy s = new BookFineStrategy();
        assertEquals(0, s.calculateFine(0));
        assertEquals(10, s.calculateFine(1));
        assertEquals(50, s.calculateFine(5));
    }

    @Test
    void cdFine_isTwentyNisPerDay_US52() {
        FineStrategy s = new CDFineStrategy();
        assertEquals(0, s.calculateFine(0));
        assertEquals(20, s.calculateFine(1));
        assertEquals(60, s.calculateFine(3));
    }
}