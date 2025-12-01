package library.borrow;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import library.fines.BookFineStrategy;
import library.fines.CDFineStrategy;
import library.fines.FineStrategy;
import library.media.Media;
import library.users.User;

public class BorrowManager {

    private List<Borrow> borrows;

    public BorrowManager() {
        borrows = new ArrayList<>();
    }
 
    public void borrowMedia(Media media, User user) {
        if (!user.canBorrow()) {
            System.out.println("User has unpaid fines. Cannot borrow.");
            return;
        }
        Borrow b = new Borrow(media, user);
        borrows.add(b);
        System.out.println(media.getTitle() + " borrowed. Due: " +
                LocalDate.now().plusDays(media.getBorrowDays()));
    }

    public void checkOverdue() {
        for (Borrow b : borrows) {
            if (b.isOverdue()) {

                long daysLate = ChronoUnit.DAYS.between(
                        b.getDueDate(),
                        LocalDate.now()
                );

                FineStrategy strategy;
                if ("CD".equals(b.getMedia().getType())) {
                    strategy = new CDFineStrategy();
                } else {
                    strategy = new BookFineStrategy();
                }

                double fine = strategy.calculateFine(daysLate);
                b.getUser().addFine(fine);

                System.out.println("Overdue: " + b.getMedia().getTitle()
                        + " | Days late: " + daysLate
                        + " | Fine added: " + fine);
            }
        }
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public void listBorrows() {
        if (borrows.isEmpty()) {
            System.out.println("No borrows yet.");
            return;
        }
        for (Borrow b : borrows) {
            System.out.println(b.getMedia().getTitle() + " borrowed by " + b.getUser().getName());
        }
    }

    public void payFine(User user, double amount) {
        user.payFine(amount);
    }

   
    public List<User> getAllUsersWithOverdues() {
        List<User> result = new ArrayList<>();
        for (Borrow b : borrows) {
            if (b.isOverdue() && !result.contains(b.getUser())) {
                result.add(b.getUser());
            }
        }
        return result;
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
