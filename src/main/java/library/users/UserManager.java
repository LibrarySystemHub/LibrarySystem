package library.users;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import library.admin.Admin;

public class UserManager {

    private List<User> users;
    public UserManager() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }
    public boolean unregisterUser(Admin admin, User user) {
    	if (!admin.isLoggedIn()) {
            System.out.println("Only admins can unregister users!");
            return false;
        }
    	if (!user.canBorrow()) { 
            System.out.println("Cannot unregister user: unpaid fines exist!");
            return false;
    	}
    	 boolean removed = users.remove(user);
         if (removed) {
             System.out.println("User " + user.getName() + " unregistered successfully.");
         } else {
             System.out.println("User not found.");
         }
         return removed;	
    }
    

}
