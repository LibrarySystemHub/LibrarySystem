package library.borrow;

import java.time.LocalDate;
import library.books.Book;
import library.users.User;

public class Borrow {
    private Book book;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    public Borrow(Book book, User user) {
        this.book = book;
        this.user = user;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(28);
        this.returned = false;
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
}
