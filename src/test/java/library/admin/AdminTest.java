package library.admin;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class AdminTest {
	 private Admin admin; 
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		admin = new Admin("alaa", "1234");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoginWithValidCredentials() {
		admin.login("alaa", "1234");
		assertTrue(admin.isLoggedIn());

	}
	@Test
	void testLoginWithInvalidCredentials() {
		 admin.login("alaa", "124");
		 assertFalse(admin.isLoggedIn());
	}
	@Test
	void testLogout() {
		 admin.login("admin", "1234");
		 admin.logout();
		 assertFalse(admin.isLoggedIn());

	}
	
	

}
