package library.users;
/**
 * Represents a library user with a name, email, password, and fine balance.
 * This class provides methods to manage fines and check borrowing eligibility.
 * 
 * @author Alaa
 * @author Jana
 * @version 1.0
 */

public class User {
    private String name;
    private double fineBalance;
    
    private String email;
    private String password;
 
    /**
     * Creates a user with the given name and zero fine balance.
     * 
     * @param name the name of the user
     */
    public User(String name) {
        this.name = name;
        this.fineBalance = 0.0;
    }

    /**
     * Creates a user with name, password, and email, with zero fine balance.
     * 
     * @param name the name of the user
     * @param password the user's password
     * @param email the user's email
     */
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.fineBalance = 0.0;
    }
    /** @return the user's name */
    public String getName() {
        return name;
    }
    /** @return the current fine balance */
    public double getFineBalance() {
        return fineBalance;
    }
    /** @return the user's email */
    public String getEmail() {
        return email;
    }
    /** @return the user's password */
    public String getPassword() {
        return this.password;
    }
    /**
     * Checks if the provided password matches the user's password.
     * 
     * @param inputPassword the password to check
     * @return true if passwords match, false otherwise
     */
    public boolean checkPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }
    /**
     * Pays a fine, reducing the fine balance by the specified amount.
     * Prints status messages to the console.
     * 
     * @param amount the amount to pay
     */

    public void payFine(double amount) {
        if (amount <= 0) {
            System.out.println("Amount must be positive.");
            return;
        }

        if (fineBalance == 0) {
            System.out.println("No fines to pay!");
            return;
        }

        if (amount > fineBalance) amount = fineBalance;
        fineBalance -= amount;
        System.out.println("You paid: " + amount + " NIS. Remaining fine: " + fineBalance);
    }
    /**
     * Adds a fine to the user's balance.
     * 
     * @param amount the amount of fine to add
     */
    public void addFine(double amount) {
        fineBalance += amount;
    }
    /**
     * Checks if the user is allowed to borrow media (no fines).
     * 
     * @return true if the user can borrow, false otherwise
     */
    public boolean canBorrow() {
        return fineBalance <= 0;
    }
}
