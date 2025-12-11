package library.notifications;

import library.users.User;
/**
 * Observer interface for receiving notifications about users.
 * Any class implementing this interface should define how to notify users.
 * 
 * @author Jana
 * @version 1.0
 */
public interface Observer {
	/**
     * Notify a user with a given message.
     * 
     * @param user the user to notify
     * @param message the notification message
     */
    void notify(User user, String message);
}
