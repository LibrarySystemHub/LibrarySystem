package library.admin;

public class Admin {
	private String username;
	private String password;
	private boolean loggedIn;
	
	public Admin(String username,String password) {
		this.username = username;
		this.password = password;
		
		loggedIn = false;
	}
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
	
	public void logout() {
		loggedIn = false;
		System.out.println("Logged out successfully!");
	}
	
	public boolean isLoggedIn() {
	    return loggedIn;
	}

}
