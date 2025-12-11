package library.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import library.Config;

class ConfigTest {

    @Test
    void testGetAdminUsername() {
        String username = Config.get("ADMIN_USERNAME");
        assertEquals("alaa", username);
    }

    @Test
    void testGetAdminPassword() {
        String password = Config.get("ADMIN_PASSWORD");
        assertEquals("1234", password);
    }

    @Test
    void testGetInvalidKeyReturnsNull() {
        assertNull(Config.get("UNKNOWN_KEY"));
    }
}
