package library.notifications;

import java.util.ArrayList;
import java.util.List;

import library.borrow.Borrow;
import library.borrow.BorrowManager;
import library.users.User;
/**
 * Service that sends reminders to users with overdue borrows.
 * Uses the Observer pattern to notify observers (e.g., email notifiers).
 * 
 * @author Jana
 * @version 1.0
 */
public class ReminderService {

    private List<Observer> observers = new ArrayList<>();
    private BorrowManager borrowManager;
    /**
     * Creates a ReminderService for the given BorrowManager.
     * 
     * @param borrowManager the BorrowManager instance to track borrows
     */
    public ReminderService(BorrowManager borrowManager) {
        this.borrowManager = borrowManager;
    }
    /**
     * Adds an observer that will be notified about overdue borrows.
     * 
     * @param observer the observer to add
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    /**
     * Sends reminders to all users who have overdue borrows.
     * Each observer in the observers list will be notified with a message.
     */
    public void sendOverdueReminders() {

        for (User user : borrowManager.getAllUsersWithOverdues()) {

            int overdueCount = borrowManager.countOverduesForUser(user);

            String message = "You have " + overdueCount + " overdue book(s).";

            for (Observer obs : observers) {
                obs.notify(user, message);
            }
        }
    }
}
