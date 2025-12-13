package library.Borrow;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.borrow.Borrow;
import library.borrow.BorrowManager;
import library.media.Book;
import library.media.CD;
import library.media.Media;
import library.users.User;
import java.nio.file.Path;
import library.StorageManager;
class BorrowManagerTest {

    private BorrowManager manager;
    private List<User> users;
    private List<Media> mediaList;
    private User user;
    private Media book;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        mediaList = new ArrayList<>();
        manager = new BorrowManager(users, mediaList);

        user = new User("jana", "123", "jana@mail.com");
        book = new Book("Clean Code", "Robert Martin", "9780132350884");

        users.add(user);
        mediaList.add(book);
    }

    @Test
    void testBorrowMediaSuccess() {
        int before = manager.getBorrows().size();

        manager.borrowMedia(book, user);

        int after = manager.getBorrows().size();
        assertEquals(before + 1, after);

        Borrow b = manager.getBorrows().get(0);
        assertEquals(book, b.getMedia());
        assertEquals(user, b.getUser());
        assertEquals(LocalDate.now().plusDays(book.getBorrowDays()), b.getDueDate());
    }

    @Test
    void testBorrowBlockedWhenUserHasFine() {
        user.addFine(10);

        int before = manager.getBorrows().size();
        manager.borrowMedia(book, user);
        int after = manager.getBorrows().size();

        assertEquals(before, after);
    }

    @Test
    void testBorrowBlockedWhenUserHasOverdue() {
        manager.borrowMedia(book, user);

        Borrow b = manager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(3));

        int before = manager.getBorrows().size();
        manager.borrowMedia(new Book("Java", "Alaa", "111"), user);
        int after = manager.getBorrows().size();

        assertEquals(before, after);
    }

    @Test
    void testCheckOverdueAddsFine() {
        manager.borrowMedia(book, user);

        Borrow b = manager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(3));

        double beforeFine = user.getFineBalance();

        manager.checkOverdue();

        long daysLate = ChronoUnit.DAYS.between(b.getDueDate(), LocalDate.now());
        double expected = beforeFine + daysLate * 10.0; // fine for books

        assertEquals(expected, user.getFineBalance());
    }

    @Test
    void testCheckOverdueForCD() {
        Media cd = new CD("Hits", "CD001");
        mediaList.add(cd);

        manager.borrowMedia(cd, user);
        Borrow b = manager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(2));

        manager.checkOverdue();

        long daysLate = ChronoUnit.DAYS.between(b.getDueDate(), LocalDate.now());
        double expected = daysLate * 20.0; // CD fine

        assertEquals(expected, user.getFineBalance());
    }

    @Test
    void testCheckOverdueWhenNotLate() {
        manager.borrowMedia(book, user);

        double beforeFine = user.getFineBalance();

        manager.checkOverdue();

        assertEquals(beforeFine, user.getFineBalance());
    }

    @Test
    void testPayFine() {
        user.addFine(10);
        manager.payFine(user, 4);
        assertEquals(6, user.getFineBalance());
    }

    @Test
    void testGetAllUsersWithOverdues() {
        User second = new User("bob", "111", "b@mail");
        users.add(second);

        manager.borrowMedia(book, user);
        manager.borrowMedia(new Book("Java", "Alaa", "111"), second);

        manager.getBorrows().get(0).setDueDate(LocalDate.now().minusDays(3));

        List<User> list = manager.getAllUsersWithOverdues();

        assertTrue(list.contains(user));
        assertFalse(list.contains(second));
    }

    @Test
    void testCountOverduesForUser() {
        manager.borrowMedia(book, user);

        Borrow b = manager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(2));

        int count = manager.countOverduesForUser(user);

        assertEquals(1, count);
    }

    @Test
    void testCountOverduesZero() {
        manager.borrowMedia(book, user);

        int count = manager.countOverduesForUser(user);

        assertEquals(0, count);
    }

    @Test
    void testListBorrowsUser_NoBorrows() {
         
        manager.listBorrows(user);
    }

    @Test
    void testListBorrowsUser_WithBorrows() {
        manager.borrowMedia(book, user);
        manager.listBorrows(user);
    }

    @Test
    void testListBorrowsAll_Empty() {
        manager.listBorrows();
    }

    @Test
    void testListBorrowsAll_WithBorrows() {
        manager.borrowMedia(book, user);
        manager.listBorrows();
    }
    @Test
void testLoadMedia_FileNotExist() {
    Path tempFile = Path.of("nonexistent.txt");
    StorageManager.useCustomPaths(tempFile, Path.of(""), Path.of(""));
    List<Media> list = StorageManager.loadMedia();
    assertTrue(list.isEmpty());
}

@Test
void testLoadUsers_FileNotExist() {
    Path tempFile = Path.of("nonexistent.txt");
    StorageManager.useCustomPaths(Path.of(""), tempFile, Path.of(""));
    List<User> list = StorageManager.loadUsers();
    assertTrue(list.isEmpty());
}

@Test
void testLoadBorrows_FileNotExist() {
    Path tempFile = Path.of("nonexistent.txt");
    StorageManager.useCustomPaths(Path.of(""), Path.of(""), tempFile);
    List<Borrow> list = StorageManager.loadBorrows(new ArrayList<>(), new ArrayList<>());
    assertTrue(list.isEmpty());
}

}
