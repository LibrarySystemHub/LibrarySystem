package library.users;

public class User {
    private String name;
    private double fineBalance;

    public User(String name) {
        this.name = name;
        this.fineBalance = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getFineBalance() {
        return fineBalance;
    }

    public void payFine(double amount) {
        if (amount <= 0) {
            System.out.println("⚠️ Amount must be positive.");
            return;
        }

        if (fineBalance == 0) {
            System.out.println("✅ No fines to pay!");
            return;
        }

        if (amount > fineBalance) amount = fineBalance;
        fineBalance -= amount;
        System.out.println("💰 You paid: " + amount + " NIS. Remaining fine: " + fineBalance);
    }

    public void addFine(double amount) {
        fineBalance += amount;
    }

    public boolean canBorrow() {
        return fineBalance <= 0;
    }
}
