package library.media;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CDTest {

    @Test
    void testCDConstructorAndGetters() {
        CD cd = new CD("Best Songs", "S123");

        assertEquals("Best Songs", cd.getTitle());
        assertEquals("S123", cd.getId());
        assertEquals("CD", cd.getType());
        assertEquals(7, cd.getBorrowDays());
    }
    @Test
    void testToStringCD() {
        CD cd = new CD("Best Songs", "CD001");
        String result = cd.toString();
        assertTrue(result.contains("Best Songs"));
        assertTrue(result.contains("CD001"));
    }

}
