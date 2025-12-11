package library.Borrow;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.borrow.Borrow;
import library.media.Book;
import library.users.User;

class BorrowTest {

    private Book book;
    private User user;
    private Borrow borrow;

    @BeforeEach
    void setUp() {
        book = new Book("Clean Code", "Robert Martin", "9780132350884");
        user = new User("jana");
        borrow = new Borrow(book, user);
    }

    
    private void setDueDate(Borrow b, LocalDate date) {
        try {
            Field field = Borrow.class.getDeclaredField("dueDate");
            field.setAccessible(true);
            field.set(b, date);
        } catch (Exception e) {
            fail("Failed to modify dueDate for test.");
        }
    }

    @Test
    void testInitialDueDateIs28DaysFromToday() {
        LocalDate today = LocalDate.now();
        assertEquals(today.plusDays(28), borrow.getDueDate());
    }

    @Test
    void testIsOverdueFalseWhenNotPastDueAndNotReturned() {
        assertFalse(borrow.isOverdue());
    }

    @Test
    void testIsOverdueTrueWhenPastDueAndNotReturned() {
        LocalDate oldDate = LocalDate.now().minusDays(3);
        setDueDate(borrow, oldDate);
        assertTrue(borrow.isOverdue());
    }

    @Test
    void testIsOverdueFalseWhenReturnedEvenIfPastDue() {
        LocalDate oldDate = LocalDate.now().minusDays(5);
        setDueDate(borrow, oldDate);
        borrow.setReturned(true);
        assertFalse(borrow.isOverdue());
    }
    @Test
    void testGetters() {
        assertEquals(book, borrow.getMedia());
        assertEquals(user, borrow.getUser());
        assertEquals(false, borrow.isReturned());
        assertEquals(LocalDate.now(), borrow.getBorrowDate());
        assertEquals(LocalDate.now().plusDays(28), borrow.getDueDate());
    }
    @Test
    void testSetReturned() {
        borrow.setReturned(true);
        assertTrue(borrow.isReturned());

        borrow.setReturned(false);
        assertFalse(borrow.isReturned());
    }
    @Test
    void testSetBorrowDate() {
        LocalDate customDate = LocalDate.of(2022, 1, 1);
        borrow.setBorrowDate(customDate);
        assertEquals(customDate, borrow.getBorrowDate());
    }
    @Test
    void testSetDueDate() {
        LocalDate customDue = LocalDate.of(2023, 5, 10);
        borrow.setDueDate(customDue);
        assertEquals(customDue, borrow.getDueDate());
    }
    @Test
    void testIsOverdueWhenDueDateIsToday() {
        setDueDate(borrow, LocalDate.now());
        assertFalse(borrow.isOverdue());
    }
}
