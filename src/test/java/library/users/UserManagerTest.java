package library.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mockStatic;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import library.StorageManager;
import library.admin.Admin;
import library.borrow.Borrow;
import library.borrow.BorrowManager;

class UserManagerTest {

	 private UserManager userManager;
	    private Admin admin;
	    private User user1;
	    private User user2;
	    private BorrowManager manager;
	    private MockedStatic<StorageManager> storageMock;

    @BeforeEach
    void setUp() {
    	storageMock = mockStatic(StorageManager.class);
        storageMock.when(() -> StorageManager.saveUsers(anyList()))
                   .thenAnswer(invocation -> null);
        storageMock.when(() -> StorageManager.saveBorrows(anyList()))
                   .thenAnswer(invocation -> null);

        userManager = new UserManager(new ArrayList<>());

        admin = new Admin("alaa", "1234");
        admin.login("alaa", "1234");

        user1 = new User("jana");
        user2 = new User("aya");
        user2.addFine(10);

        userManager.addUser(user1);
        userManager.addUser(user2);

        manager = new BorrowManager(new ArrayList<>(), new ArrayList<>());
    }
    @AfterEach
    void tearDown() {
        storageMock.close();
    }

    @Test
    void testAddUser() {
        User u = new User("newUser");
        int before = userManager.getUsers().size();
        userManager.addUser(u);
        assertEquals(before + 1, userManager.getUsers().size());
        assertTrue(userManager.getUsers().contains(u));
    }

    @Test
    void testFindUserByName() {
        assertEquals(user1, userManager.findUserByName("jana"));
        assertNull(userManager.findUserByName("nonexist"));
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
        User user3 = new User("ghost");
        boolean result = userManager.unregisterUser(admin, user3, manager);
        assertFalse(result);
    }

    @Test
    void testUnregisterUserRemovesBorrows() {
       
        Borrow borrow = new Borrow(new library.media.Book("Book1", "Author", "111"), user1);
        manager.getBorrows().add(borrow);

        boolean result = userManager.unregisterUser(admin, user1, manager);

        assertTrue(result);
        assertFalse(manager.getBorrows().contains(borrow)); 
    }
}
