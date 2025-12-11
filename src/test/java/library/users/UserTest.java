package library.users;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        user = new User("jana");
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testInitialFineBalanceIsZero() {
        assertEquals(0.0, user.getFineBalance());
    }

    @Test
    void testAddFineIncreasesFineBalance() {
        user.addFine(5.0);
        assertEquals(5.0, user.getFineBalance());
    }

    @Test
    void testPayFineReducesBalance() {
        user.addFine(10.0);
        user.payFine(4.0);
        assertEquals(6.0, user.getFineBalance());
    }

    @Test
    void testPayFineMoreThanBalanceCapsAtZero() {
        user.addFine(8.0);
        user.payFine(20.0);
        assertEquals(0.0, user.getFineBalance());
    }

    @Test
    void testPayFineWhenNoFineKeepsZero() {
        user.payFine(5.0);
        assertEquals(0.0, user.getFineBalance());
    }

    @Test
    void testCanBorrowWhenNoFine() {
        assertTrue(user.canBorrow());
    }

    @Test
    void testCannotBorrowWhenHasFine() {
        user.addFine(3.0);
        assertFalse(user.canBorrow());
    }
    @Test
    void testCheckPasswordCorrect() {
        User u = new User("ala", "pass123", "ala@mail.com");
        assertTrue(u.checkPassword("pass123"));
    }

    @Test
    void testCheckPasswordIncorrect() {
        User u = new User("ala", "pass123", "ala@mail.com");
        assertFalse(u.checkPassword("wrongpass"));
    }

    @Test
    void testCheckPasswordNullPasswordField() {
        User u = new User("ala"); 
        assertFalse(u.checkPassword("anything"));
    }

    @Test
    void testPayFineWithNegativeAmount() {
        user.addFine(5.0);
        user.payFine(-3.0);
        assertEquals(5.0, user.getFineBalance()); 
    }

    @Test
    void testPayFineWithZeroAmount() {
        user.addFine(5.0);
        user.payFine(0.0);
        assertEquals(5.0, user.getFineBalance()); 
    }

    @Test
    void testPayFineWhenBalanceZero() {
        assertEquals(0.0, user.getFineBalance());
        user.payFine(10.0);
        assertEquals(0.0, user.getFineBalance()); 
    }
}
