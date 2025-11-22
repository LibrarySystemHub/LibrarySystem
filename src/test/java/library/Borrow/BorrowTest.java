package library.Borrow;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.books.Book;
import library.borrow.Borrow;
import library.users.User;

class BorrowTest {

    private Book book;
    private User user;
    private Borrow borrow;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        book = new Book("Clean Code", "Robert Martin", "9780132350884");
        user = new User("jana");
        borrow = new Borrow(book, user);
    }

    @AfterEach
    void tearDown() throws Exception {
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
        borrow.setDueDate(oldDate);
        assertTrue(borrow.isOverdue());
    }

    @Test
    void testIsOverdueFalseWhenReturnedEvenIfPastDue() {
        LocalDate oldDate = LocalDate.now().minusDays(5);
        borrow.setDueDate(oldDate);
        borrow.setReturned(true);
        assertFalse(borrow.isOverdue());
    }
}
