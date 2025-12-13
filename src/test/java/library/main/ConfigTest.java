package library.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import library.Config;

class ConfigTest {

    @BeforeAll
    static void setup() {
        System.setProperty("ADMIN_USERNAME", "alaa");
        System.setProperty("ADMIN_PASSWORD", "1234");
    }

    @Test
    void testGetAdminUsername() {
        assertEquals("alaa", Config.get("ADMIN_USERNAME"));
    }

    @Test
    void testGetAdminPassword() {
        assertEquals("1234", Config.get("ADMIN_PASSWORD"));
    }

    @Test
    void testGetInvalidKeyReturnsNull() {
        assertNull(Config.get("UNKNOWN_KEY"));
    }
}
