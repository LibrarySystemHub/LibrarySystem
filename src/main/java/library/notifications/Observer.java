package library.notifications;

import library.users.User;

public interface Observer {
    void notify(User user, String message);
}
