package library.users;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.admin.Admin;
import library.borrow.BorrowManager;

class UserManagerTest {

    private UserManager userManager;
    private Admin admin;
    private User user1;
    private User user2;
    private BorrowManager manager;

    @BeforeEach
    void setUp() {

       
        userManager = new UserManager(new ArrayList<>());

        admin = new Admin("alaa", "1234");
        admin.login("alaa", "1234");

        user1 = new User("jana");
        user2 = new User("aya");
        user2.addFine(10);

        userManager.addUser(user1);
        userManager.addUser(user2);

        manager = new BorrowManager();
    }

    @Test
    void testUnregisterUserSuccess() {
        boolean result = userManager.unregisterUser(admin, user1, manager);
        assertTrue(result);
        assertFalse(userManager.getUsers().contains(user1));
    }

    @Test
    void testUnregisterUserFailsDueToFine() {
        boolean result = userManager.unregisterUser(admin, user2, manager);
        assertFalse(result);
        assertTrue(userManager.getUsers().contains(user2));
    }

    @Test
    void testUnregisterUserFailsIfNotAdmin() {
        Admin fakeAdmin = new Admin("fake", "123");
        boolean result = userManager.unregisterUser(fakeAdmin, user1, manager);
        assertFalse(result);
        assertTrue(userManager.getUsers().contains(user1));
    }

    @Test
    void testUnregisterNonExistingUser() {
        User user3 = new User("nonexist");
        boolean result = userManager.unregisterUser(admin, user3, manager);
        assertFalse(result);
    }
}
