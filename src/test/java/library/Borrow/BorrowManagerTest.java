package library.Borrow;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.borrow.Borrow;
import library.borrow.BorrowManager;
import library.media.Book;
import library.media.Media;
import library.users.User;

class BorrowManagerTest {

    private BorrowManager borrowManager;
    private User user;
    private Media book;

    @BeforeEach
    void setUp() {
        borrowManager = new BorrowManager();
        user = new User("jana", "123", "jana@mail.com");
        book = new Book("Clean Code", "Robert Martin", "9780132350884");
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
        user.addFine(10); // user cannot borrow

        int before = borrowManager.getBorrows().size();
        borrowManager.borrowMedia(book, user);
        int after = borrowManager.getBorrows().size();

        assertEquals(before, after);
    }

    
    @Test
    void testBorrowBlockedWhenUserHasOverdue() {
        borrowManager.borrowMedia(book, user);

        Borrow oldBorrow = borrowManager.getBorrows().get(0);
        oldBorrow.setDueDate(LocalDate.now().minusDays(3)); // overdue

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

        LocalDate overdueDate = LocalDate.now().minusDays(3);
        b.setDueDate(overdueDate);

        double beforeFine = user.getFineBalance();

        borrowManager.checkOverdue();

        long daysLate = ChronoUnit.DAYS.between(overdueDate, LocalDate.now());
        double expected = beforeFine + daysLate * 10.0; // BookFineStrategy = 10 NIS/day

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
}
