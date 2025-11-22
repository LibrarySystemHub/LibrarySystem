package library.borrow;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import library.books.Book;
import library.users.User;

public class BorrowManager {

    private ArrayList<Borrow> borrows;

    public BorrowManager() {
        borrows = new ArrayList<>();
    }

    public void borrowBook(Book book, User user) {
        if (!user.canBorrow()) {
            System.out.println("You have unpaid fines! Pay before borrowing.");
            return;
        }

        Borrow newBorrow = new Borrow(book, user);
        borrows.add(newBorrow);
        System.out.println("Book borrowed successfully. Due on: " + newBorrow.getDueDate());
    }

    public void checkOverdueBooks() {
        boolean found = false;

        for (Borrow b : borrows) {
            if (b.isOverdue()) {
                found = true;
                long daysLate = ChronoUnit.DAYS.between(b.getDueDate(), java.time.LocalDate.now());
                double fine = daysLate * 1.0;
                b.getUser().addFine(fine);
                System.out.println("Overdue: " + b.getBook().getTitle() +
                        " | Late: " + daysLate +
                        " | Fine: " + fine);
            }
        }

        if (!found) System.out.println("No overdue books.");
    }

    public void payFine(User user, double amount) {
        user.payFine(amount);
    }

    public void listBorrows() {
        if (borrows.isEmpty()) {
            System.out.println("No borrowed books yet.");
            return;
        }

        for (Borrow b : borrows) {
            System.out.println(b);
        }
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public List<User> getAllUsersWithOverdues() {
        List<User> users = new ArrayList<>();

        for (Borrow b : borrows) {
            if (b.isOverdue() && !users.contains(b.getUser())) {
                users.add(b.getUser());
            }
        }

        return users;
    }

    public int countOverduesForUser(User user) {
        int count = 0;

        for (Borrow b : borrows) {
            if (b.getUser().equals(user) && b.isOverdue()) {
                count++;
            }
        }

        return count;
    }
}
