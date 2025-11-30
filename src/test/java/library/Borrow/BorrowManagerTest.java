package library.Borrow;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.books.Book;
import library.borrow.Borrow;
import library.borrow.BorrowManager;
import library.users.User;

class BorrowManagerTest {

    private BorrowManager borrowManager;
    private User user;
    private Book book;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        borrowManager = new BorrowManager();
        user = new User("jana");
        book = new Book("Clean Code", "Robert Martin", "9780132350884");
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testBorrowBookWhenUserCanBorrowAddsBorrow() {
        List<Borrow> borrows = borrowManager.getBorrows();
        int before = borrows.size();

        borrowManager.borrowBook(book, user);

        int after = borrows.size();
        assertEquals(before + 1, after);

        Borrow lastBorrow = borrows.get(after - 1);
        assertEquals(book, lastBorrow.getBook());
        assertEquals(user, lastBorrow.getUser());
        assertEquals(LocalDate.now().plusDays(28), lastBorrow.getDueDate());
    }

    @Test
    void testBorrowBookWhenUserHasUnpaidFineIsBlocked() {
        user.addFine(5.0);

        List<Borrow> borrows = borrowManager.getBorrows();
        int before = borrows.size();

        borrowManager.borrowBook(book, user);

        int after = borrows.size();
        assertEquals(before, after);
    }
    @Test
    void testBorrowBlockedWhenUserHasOverdueBorrow() {
    	borrowManager.borrowBook(book, user);
        Borrow existingBorrow = borrowManager.getBorrows().get(0);
        existingBorrow.setDueDate(LocalDate.now().minusDays(5));
        int before = borrowManager.getBorrows().size();
        Book newBook = new Book("Java", "Alaa", "222");
        borrowManager.borrowBook(newBook, user);
        int after = borrowManager.getBorrows().size();
        assertEquals(before, after);
    }

    @Test
    void testCheckOverdueBooksAddsFineForOverdueBorrow() {
        borrowManager.borrowBook(book, user);

        List<Borrow> borrows = borrowManager.getBorrows();
        Borrow borrow = borrows.get(0);

        LocalDate overdueDate = LocalDate.now().minusDays(3);
        borrow.setDueDate(overdueDate);

        double beforeFine = user.getFineBalance();

        borrowManager.checkOverdueBooks();

        long daysLate = ChronoUnit.DAYS.between(overdueDate, LocalDate.now());
        double expectedFine = beforeFine + daysLate * 1.0;

        assertEquals(expectedFine, user.getFineBalance());
    }

    @Test
    void testCheckOverdueBooksDoesNotAddFineWhenNotOverdue() {
        borrowManager.borrowBook(book, user);

        double beforeFine = user.getFineBalance();

        borrowManager.checkOverdueBooks();

        assertEquals(beforeFine, user.getFineBalance());
    }

    @Test
    void testPayFineThroughBorrowManager() {
        user.addFine(10.0);
        borrowManager.payFine(user, 4.0);
        assertEquals(6.0, user.getFineBalance());
    }
}
