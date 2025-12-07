package library.borrow;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import library.StorageManager;
import library.books.Book;
import library.users.User;

public class BorrowManager {

    private ArrayList<Borrow> borrows;
    private List<User> users;
    private boolean testMode;

    public BorrowManager(List<Book> books, List<User> users) {
        borrows = StorageManager.loadBorrows(books, users); 
        this.users = users;
        testMode = false;
    }
    public BorrowManager(boolean testMode) {
        borrows = new ArrayList<>();
        this.testMode = testMode;
    }

    public void borrowBook(Book book, User user) {
        if (!user.canBorrow()) {
            System.out.println("You have unpaid fines! Pay before borrowing.");
            return;
        }
        for (Borrow b : borrows) {
            if (b.getUser().equals(user) && b.isOverdue()) {
            	System.out.println("Cannot borrow: User has overdue books.");
            	return;
            }
        }

        Borrow newBorrow = new Borrow(book, user);
        borrows.add(newBorrow);
        if (!testMode) {
            StorageManager.saveBorrows(borrows);
        }
        System.out.println("Book borrowed successfully. Due on: " + newBorrow.getDueDate());
    }
    public void returnBook(Borrow borrow) {
        borrow.setReturned(true);
        
        if (!testMode) {
            StorageManager.saveBorrows(borrows);
        }
        System.out.println("Book returned successfully: " + borrow.getBook().getTitle());
    }

    public void checkOverdueBooks() {
        boolean found = false;

        for (Borrow b : borrows) {
            if (b.isOverdue()) {
                found = true;
                double fineAddedToday = 0;
                LocalDate today = LocalDate.now();
                if (b.getLastFineChecked().isBefore(today)) {
                    fineAddedToday = b.calculateFine();
                }
                System.out.println("User: " + b.getUser().getName() +
                        " | Overdue: " + b.getBook().getTitle() +
                        " | Fine added today: " + fineAddedToday +
                        " | Total fine: " + b.getUser().getFineBalance());
               
            }
        }

        if (!found) System.out.println("No overdue books.");
        if (!testMode) {
        	 StorageManager.saveUsers(users);
            StorageManager.saveBorrows(borrows);
        }
    }

    public void payFine(User user, double amount) {
        user.payFine(amount);
        if (!testMode) {
        	
        	StorageManager.saveUsers(users);
        }
    }

    public void listBorrows(User user) {
    	  boolean found = false;
    	    for (Borrow b : borrows) {
    	        if (b.getUser().equals(user)) {
    	            System.out.println(b);
    	            found = true;
    	        }
    	    }
    	    if (!found) {
    	        System.out.println("No borrowed books yet.");
    	    }
    }

    public List<Borrow> getBorrows() {
        return borrows;
    }

    public List<User> getAllUsersWithOverdues() {
        List<User> users = new ArrayList<>();

        for (Borrow b : borrows) {
            if (b.isOverdue() && !users.contains(b.getUser())) {
                users.add(b.getUser());
            }
        }

        return users;
    }

    public int countOverduesForUser(User user) {
        int count = 0;

        for (Borrow b : borrows) {
            if (b.getUser().equals(user) && b.isOverdue()) {
                count++;
            }
        }

        return count;
    }
}
