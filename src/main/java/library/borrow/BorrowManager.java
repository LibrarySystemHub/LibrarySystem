package library.borrow;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import library.StorageManager;
import library.fines.BookFineStrategy;
import library.fines.CDFineStrategy;
import library.fines.FineStrategy;
import library.media.Media;
import library.users.User;

/**
 * Manages borrow operations in the library, including borrowing media,
 * checking overdue items, calculating fines, and listing borrows.
 * 
 * @author Alaa
 * @author Jana
 * @version 1.0
 */
public class BorrowManager {

    private List<Borrow> borrows;

    /**
     * Initializes the BorrowManager and loads existing borrows from storage.
     * 
     * @param users the list of users in the system
     * @param mediaList the list of media in the system
     */
    public BorrowManager(List<User> users, List<Media> mediaList) {
        borrows = StorageManager.loadBorrows(mediaList, users);
    }
    
    /**
     * Allows a user to borrow a media item if eligible.
     * 
     * @param media the media to borrow
     * @param user the user borrowing the media
     */

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
        StorageManager.saveBorrows(borrows);

        System.out.println(media.getTitle() + " borrowed. Due: " + newBorrow.getDueDate());
    }

    /**
     * Checks all borrows for overdue items and applies fines accordingly.
     */
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

    /** @return the list of all borrows */
    public List<Borrow> getBorrows() {
        return borrows;
    }

    /**
     * Lists all borrows for a specific user.
     * 
     * @param user the user whose borrows to list
     */
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

    /** Lists all active borrows in the system. */
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

    /**
     * Allows a user to pay a fine.
     * 
     * @param user the user paying the fine
     * @param amount the amount to pay
     */
    public void payFine(User user, double amount) {
        user.payFine(amount);
    }

    /** @return list of users who currently have overdue items */
    public List<User> getAllUsersWithOverdues() {
        List<User> list = new ArrayList<>();

        for (Borrow b : borrows) {
            if (b.isOverdue() && !list.contains(b.getUser())) {
                list.add(b.getUser());
            }
        }

        return list;
    }
    
    /**
     * Counts the number of overdue items for a specific user.
     * 
     * @param user the user to check
     * @return the number of overdue items
     */
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
