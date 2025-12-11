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
		admin = new Admin("Admin", "1234");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoginWithValidCredentials() {
		admin.login("Admin", "1234");
		assertTrue(admin.isLoggedIn());

	}
	@Test
	void testLoginWithInvalidCredentials() {
		 admin.login("Admin", "124");
		 assertFalse(admin.isLoggedIn());
	}
	@Test
	void testLogout() {
		 admin.login("Admin", "1234");
		 admin.logout();
		 assertFalse(admin.isLoggedIn());

	}
	@Test
	void testInitialLoggedInStateIsFalse() {
	    Admin a = new Admin("Admin", "1234");
	    assertFalse(a.isLoggedIn());
	}

	@Test
	void testLogoutWithoutLogin() {
	    Admin a = new Admin("Admin", "1234");
	    a.logout(); 
	    assertFalse(a.isLoggedIn());
	}
	
	

}
