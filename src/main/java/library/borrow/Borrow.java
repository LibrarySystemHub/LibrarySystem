package library.borrow;

import java.time.LocalDate;
import library.books.Book;
import library.users.User;
import java.time.temporal.ChronoUnit;

public class Borrow {
    private Book book;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;
    private LocalDate lastFineChecked;

    public Borrow(Book book, User user) {
        this.book = book;
        this.user = user;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(28);
        this.returned = false;
        this.lastFineChecked = borrowDate;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(28); 
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "Book='" + book.getTitle() + '\'' +
                ", User='" + user.getName() + '\'' +
                ", Borrow Date=" + borrowDate +
                ", Due Date=" + dueDate +
                ", Returned=" + returned +
                '}';
    }
    public double calculateFine() {
        if (returned) return 0;
        LocalDate today = LocalDate.now();
        
        if (!today.isAfter(dueDate)) return 0;

        
        LocalDate fromDate = lastFineChecked.isAfter(dueDate) ? lastFineChecked : dueDate;
        long daysLate = ChronoUnit.DAYS.between(fromDate, today);

        lastFineChecked = today; 
        double fine = daysLate * 1.0;
        user.addFine(fine); 
        return fine;
    }
    public LocalDate getLastFineChecked() {
        return lastFineChecked;
    }
}
