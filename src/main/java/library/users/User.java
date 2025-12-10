package library.users;

public class User {
    private String name;
    private double fineBalance;
    
    private String email;
    private String password;
 

    public User(String name) {
        this.name = name;
        this.fineBalance = 0.0;
    }
    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.fineBalance = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getFineBalance() {
        return fineBalance;
    }
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

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

    public void addFine(double amount) {
        fineBalance += amount;
    }

    public boolean canBorrow() {
        return fineBalance <= 0;
    }
}
