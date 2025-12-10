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

   
        for (Borrow b : borrows) {
            if (b.getUser().equals(user) && b.isOverdue()) {
                System.out.println("Cannot borrow: user has overdue items.");
                return;
            }
        }

        Borrow newBorrow = new Borrow(media, user);
        borrows.add(newBorrow);

        System.out.println(media.getTitle() + " borrowed. Due: " + newBorrow.getDueDate());
    }

    public void checkOverdue() {

        for (Borrow b : borrows) {

            if (b.isOverdue()) {

                long lateDays = ChronoUnit.DAYS.between(b.getDueDate(), LocalDate.now());

                FineStrategy strategy;

                if (b.getMedia().getType().equals("CD")) {
                    strategy = new CDFineStrategy();
                } else {
                    strategy = new BookFineStrategy();
                }

                double fine = strategy.calculateFine(lateDays);
                b.getUser().addFine(fine);

                System.out.println(
                        "Overdue: " + b.getMedia().getTitle() +
                        " | Late: " + lateDays +
                        " days | Fine added: " + fine
                );
            }
        }
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    
    public void listBorrows(User user) {
        boolean found = false;

        for (Borrow b : borrows) {
            if (b.getUser().equals(user)) {
                System.out.println(b.getMedia().getTitle() + " | Due: " + b.getDueDate());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No borrows for this user.");
        }
    }

    public void listBorrows() {
        if (borrows.isEmpty()) {
            System.out.println("No active borrows.");
            return;
        }

        for (Borrow b : borrows) {
            System.out.println(
                b.getMedia().getType() + ": " + b.getMedia().getTitle() +
                " borrowed by " + b.getUser().getName() +
                " | Due: " + b.getDueDate()
            );
        }
    }

    public void payFine(User user, double amount) {
        user.payFine(amount);
    }

    public List<User> getAllUsersWithOverdues() {
        List<User> list = new ArrayList<>();

        for (Borrow b : borrows) {
            if (b.isOverdue() && !list.contains(b.getUser())) {
                list.add(b.getUser());
            }
        }

        return list;
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
