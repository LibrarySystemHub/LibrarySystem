package library.notifications;

import library.users.User;

public class EmailNotifier implements Observer {

    @Override
    public void notify(User user, String message) {
        System.out.println("Email sent to " + user.getName() + ": " + message);
    }
}

