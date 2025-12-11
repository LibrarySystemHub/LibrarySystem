package library.fines;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BookFineStrategyTest {

    @Test
    void testCalculateFine() {
        BookFineStrategy strategy = new BookFineStrategy();

        
        assertEquals(0, strategy.calculateFine(0));

        
        assertEquals(10, strategy.calculateFine(1)); // assuming 10 per day

        
        assertEquals(50, strategy.calculateFine(5)); // 5 days * 10

       
        assertEquals(1000, strategy.calculateFine(100));
    }

    @Test
    void testNegativeDaysReturnZero() {
        BookFineStrategy strategy = new BookFineStrategy();

        assertEquals(0, strategy.calculateFine(-3));
    }
}
