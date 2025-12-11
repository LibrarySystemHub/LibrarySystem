package library.admin;
/**
 * Represents an admin user for the library system.
 * Handles login and logout functionality.
 * 
 * @author Alaa
 * @version 1.0
 */
public class Admin {
	private String username;
	private String password;
	private boolean loggedIn;
	
	/**
     * Creates an Admin with a username and password.
     * Initially not logged in.
     * 
     * @param username the admin's username
     * @param password the admin's password
     */
	public Admin(String username,String password) {
		this.username = username;
		this.password = password;
		
		loggedIn = false;
	}
	
	 /**
     * Attempts to log in the admin with the given credentials.
     * 
     * @param inUsername the username provided
     * @param inPassword the password provided
     */
	public void login(String inUsername,String inPassword) {
		if(username.equals(inUsername) && password.equals(inPassword)) {
			loggedIn = true;
			System.out.println("Login successful!");
		}
		else {
	        loggedIn = false;
	        System.out.println("Invalid credentials!");
	    }
	}
	
	/**
     * Logs out the admin.
     */
	public void logout() {
		loggedIn = false;
		System.out.println("Logged out successfully!");
	}
	
	 /**
     * Checks if the admin is currently logged in.
     * 
     * @return true if logged in, false otherwise
     */
	public boolean isLoggedIn() {
	    return loggedIn;
	}

}

