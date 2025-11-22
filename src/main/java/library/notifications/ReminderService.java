package library.notifications;

import java.util.ArrayList;
import java.util.List;

import library.borrow.Borrow;
import library.borrow.BorrowManager;
import library.users.User;

public class ReminderService {

    private List<Observer> observers = new ArrayList<>();
    private BorrowManager borrowManager;

    public ReminderService(BorrowManager borrowManager) {
        this.borrowManager = borrowManager;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

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
