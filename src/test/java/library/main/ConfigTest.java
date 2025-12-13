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
@Test
void testGetUnknownKeyWithEnvAndDotenvPresentReturnsNull() {
    assertNull(Config.get("NOT_EXISTING_KEY_123"));
}
     @Test
    void testGetWithDotenvNotNull() throws Exception {
        Field dotenvField = Config.class.getDeclaredField("dotenv");
        dotenvField.setAccessible(true);
        Dotenv fakeDotenv = Dotenv.configure()
                                   .ignoreIfMalformed()
                                   .ignoreIfMissing()
                                   .load();
        dotenvField.set(null, fakeDotenv);
        assertNull(Config.get("RANDOM_KEY_FOR_COVERAGE"));
        dotenvField.set(null, null);
    }
}
