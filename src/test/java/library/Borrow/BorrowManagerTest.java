package library.Borrow;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import library.StorageManager;
import library.borrow.Borrow;
import library.borrow.BorrowManager;
import library.media.Book;
import library.media.Media;
import library.users.User;

class BorrowManagerTest {

    
    private User user;
    private Media book;
    private BorrowManager borrowManager;
    private MockedStatic<StorageManager> storageMock;

    @BeforeEach
    void setUp() {   	
            
        storageMock = mockStatic(StorageManager.class);
        storageMock.when(() -> StorageManager.loadBorrows(anyList(), anyList()))
                   .thenReturn(new ArrayList<>());
        storageMock.when(() -> StorageManager.saveBorrows(anyList()))
                   .thenAnswer(invocation -> null);
        borrowManager = new BorrowManager(new ArrayList<>(), new ArrayList<>());
        user = new User("jana", "123", "jana@mail.com");
        book = new Book("Clean Code", "Robert Martin", "9780132350884");
        
    }
    @AfterEach
    void tearDown() {
       
        storageMock.close();
    }

    
    @Test
    void testBorrowMediaSuccess() {
        int before = borrowManager.getBorrows().size();
        borrowManager.borrowMedia(book, user);
        int after = borrowManager.getBorrows().size();
        assertEquals(before + 1, after);
        Borrow last = borrowManager.getBorrows().get(after - 1);
        assertEquals(book, last.getMedia());
        assertEquals(user, last.getUser());
        assertEquals(LocalDate.now().plusDays(book.getBorrowDays()), last.getDueDate());
    }

    @Test
    void testBorrowBlockedWhenUserHasFine() {
        user.addFine(10);
        int before = borrowManager.getBorrows().size();
        borrowManager.borrowMedia(book, user);
        int after = borrowManager.getBorrows().size();
        assertEquals(before, after);
    }

    @Test
    void testBorrowBlockedWhenUserHasOverdue() {
        borrowManager.borrowMedia(book, user);
        Borrow oldBorrow = borrowManager.getBorrows().get(0);
        oldBorrow.setDueDate(LocalDate.now().minusDays(3));
        int before = borrowManager.getBorrows().size();
        Media newBook = new Book("Java", "Alaa", "111");
        borrowManager.borrowMedia(newBook, user);
        int after = borrowManager.getBorrows().size();
        assertEquals(before, after);
    }

    @Test
    void testCheckOverdueAddsFine() {
        borrowManager.borrowMedia(book, user);
        Borrow b = borrowManager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(3));
        double beforeFine = user.getFineBalance();
        borrowManager.checkOverdue();
        long daysLate = ChronoUnit.DAYS.between(b.getDueDate(), LocalDate.now());
        double expected = beforeFine + daysLate * 10.0;
        assertEquals(expected, user.getFineBalance());
    }

    @Test
    void testCheckOverdueWhenNotLate() {
        borrowManager.borrowMedia(book, user);
        double before = user.getFineBalance();
        borrowManager.checkOverdue();
        assertEquals(before, user.getFineBalance());
    }

    @Test
    void testPayFine() {
        user.addFine(10);
        borrowManager.payFine(user, 4);
        assertEquals(6, user.getFineBalance());
    }

    @Test
    void testGetAllUsersWithOverdues() {
        User user2 = new User("bob");
        borrowManager.borrowMedia(book, user);
        borrowManager.borrowMedia(new Book("Java", "Alaa", "111"), user2);
        Borrow b = borrowManager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(3));
        List<User> overdues = borrowManager.getAllUsersWithOverdues();
        assertTrue(overdues.contains(user));
        assertFalse(overdues.contains(user2));
    }

    @Test
    void testCountOverduesForUser() {
        borrowManager.borrowMedia(book, user);
        Borrow b = borrowManager.getBorrows().get(0);
        b.setDueDate(LocalDate.now().minusDays(2));
        int count = borrowManager.countOverduesForUser(user);
        assertEquals(1, count);
    }
    
    @Test
    void testListBorrowsForUserNoBorrows() {
        borrowManager.listBorrows(user); 
    }

    @Test
    void testListBorrowsAllEmpty() {
        borrowManager.listBorrows();
    }

    @Test
    void testListBorrowsAllNonEmpty() {
        borrowManager.borrowMedia(book, user);
        borrowManager.listBorrows();
    }


    @Test
    void testUserMultipleBorrowsSomeOverdue() {
        Borrow b1 = new Borrow(book, user);
        Borrow b2 = new Borrow(new Book("Java", "Alaa", "111"), user);
        b1.setDueDate(LocalDate.now().minusDays(3));
        borrowManager.getBorrows().add(b1);
        borrowManager.getBorrows().add(b2);
        List<User> overdues = borrowManager.getAllUsersWithOverdues();
        assertTrue(overdues.contains(user));
    }

    @Test
    void testCountOverduesForUserZero() {
        Borrow b = new Borrow(book, user);
        borrowManager.getBorrows().add(b);
        int count = borrowManager.countOverduesForUser(user);
        assertEquals(0, count);
    }

    @Test
    void testPayFineMoreThanBalance() {
        user.addFine(5);
        borrowManager.payFine(user, 10);
        assertEquals(0, user.getFineBalance());
    }
}
