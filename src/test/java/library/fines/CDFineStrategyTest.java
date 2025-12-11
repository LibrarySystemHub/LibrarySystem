package library.fines;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CDFineStrategyTest {

    @Test
    void testCalculateFine() {
        CDFineStrategy strategy = new CDFineStrategy();

        
        assertEquals(0, strategy.calculateFine(0));

    
        assertEquals(20, strategy.calculateFine(1));

        
        assertEquals(100, strategy.calculateFine(5));  // 5 days * 20

        
        assertEquals(2000, strategy.calculateFine(100));
    }

    @Test
    void testNegativeDaysShouldReturnZero() {
        CDFineStrategy strategy = new CDFineStrategy();

        
        assertEquals(0, strategy.calculateFine(-5));
    }
}
