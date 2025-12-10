package library.users;


import java.util.ArrayList;
import java.util.List;

import library.StorageManager;
import library.admin.Admin;
import library.borrow.BorrowManager;

public class UserManager {

    private List<User> users;
    private boolean testMode;
    public UserManager(List<User> users) {
    	this.users = users;
    	testMode = false;
    }
    public UserManager(boolean testMode) {
        users = new ArrayList<>();
        this.testMode = testMode;
    }

    public void addUser(User user) {
        users.add(user);
        if (!testMode) {
            StorageManager.saveUsers(users);
        }
    }
    public User findUserByName(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) return u;
        }
        return null;
    }

    public List<User> getUsers() {
        return users;
    }
    public boolean unregisterUser(Admin admin, User user , BorrowManager borrowManager) {
    	if (!admin.isLoggedIn()) {
            System.out.println("Only admins can unregister users!");
            return false;
        }
    	if (!user.canBorrow()) { 
            System.out.println("Cannot unregister user: unpaid fines exist!");
            return false;
    	}
    	 borrowManager.getBorrows().removeIf(b -> b.getUser().equals(user));
    	 boolean removed = users.remove(user);
         if (removed) {
             System.out.println("User " + user.getName() + " unregistered successfully.");
             if (!testMode) {
                 StorageManager.saveUsers(users);
                 StorageManager.saveBorrows(borrowManager.getBorrows());
             }
         } else {
             System.out.println("User not found.");
         }
         return removed;	
    }
    

}
