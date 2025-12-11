package library.users;


import java.util.List;

import library.StorageManager;
import library.admin.Admin;
import library.borrow.BorrowManager;
/**
 * Manages library users: adding, finding, listing, and unregistering users.
 * Interacts with StorageManager to persist changes.
 * 
 * @author Alaa
 * @version 1.0
 */
public class UserManager {

    private List<User> users;
    /**
     * Creates a UserManager with the given list of users.
     * 
     * @param users the initial list of users
     */
    public UserManager(List<User> users) {
    	this.users = users;
    	
    }
   
    /**
     * Adds a new user and saves the updated list to storage.
     * 
     * @param user the user to add
     */
    public void addUser(User user) {
        users.add(user);
       
            StorageManager.saveUsers(users);
        
    }
    /**
     * Finds a user by their name.
     * 
     * @param name the name to search for
     * @return the User if found, or null if not found
     */
    public User findUserByName(String name) {
        for (User u : users) {
            if (u.getName().equals(name)) return u;
        }
        return null;
    }
    /** 
     * Returns the current list of users.
     * 
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }
    /**
     * Unregisters a user if the admin is logged in and the user has no fines.
     * Removes all borrows for that user and updates storage.
     * 
     * @param admin the admin attempting to unregister the user
     * @param user the user to unregister
     * @param borrowManager the borrow manager holding borrows
     * @return true if the user was successfully unregistered, false otherwise
     */
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
             
                 StorageManager.saveUsers(users);
                 StorageManager.saveBorrows(borrowManager.getBorrows());
             
         } else {
             System.out.println("User not found.");
         }
         return removed;	
    }
    

}
