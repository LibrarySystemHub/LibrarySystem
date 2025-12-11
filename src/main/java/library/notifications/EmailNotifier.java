package library.notifications;

import library.users.User;
/**
 * Sends email notifications to users.
 * Implements the Observer interface.
 * This is a simulated notifier (prints to console).
 * 
 * @author Jana
 * @version 1.0
 */
public class EmailNotifier implements Observer {
	/**
     * Notifies a user with a given message.
     * 
     * @param user the user to notify
     * @param message the message content
     */
    @Override
    public void notify(User user, String message) {
        System.out.println("Email sent to " + user.getName() + ": " + message);
    }
}

