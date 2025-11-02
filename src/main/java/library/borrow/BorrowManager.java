package library.borrow;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import library.books.Book;
import library.users.User;
import library.borrow.Borrow;


public class BorrowManager {
    private ArrayList<Borrow> borrows;

    public BorrowManager() {
        borrows = new ArrayList<>();
    }

    // Borrow book by passing a Book + User object
    public void borrowBook(Book book, User user) {
        if (!user.canBorrow()) {
            System.out.println("⚠️ You have unpaid fines! Pay before borrowing.");
            return;
        }

        Borrow newBorrow = new Borrow(book,user);
        borrows.add(newBorrow);
        System.out.println("✅ Book borrowed successfully! Due on: " + newBorrow.getDueDate());
    }

    // Check overdue books
    public void checkOverdueBooks() {
        System.out.println("\n📅 Checking overdue books...");
        boolean found = false;

        for (Borrow b : borrows) {
            if (b.isOverdue()) {
                found = true;
                long daysLate = ChronoUnit.DAYS.between(b.getDueDate(), java.time.LocalDate.now());
                double fine = daysLate * 1.0; // 1 per day
                b.getUser().addFine(fine);
                System.out.println("❌ Overdue Book: " + b.getBook().getTitle() +
                        " | Days late: " + daysLate +
                        " | Fine added: " + fine + " NIS");
            }
        }

        if (!found) System.out.println("✅ No overdue books!");
    }

    // Pay fine (for one user)
    public void payFine(User user, double amount) {
        user.payFine(amount);
    }

    // For debugging or listing
    public void listBorrows() {
        if (borrows.isEmpty()) {
            System.out.println("No borrowed books yet.");
            return;
        }

        System.out.println("📚 Current Borrows:");
        for (Borrow b : borrows) {
            System.out.println(b);
        }
    }
}


