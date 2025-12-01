package library.borrow;

import java.time.LocalDate;
import library.media.Media;
import library.users.User;

public class Borrow {

    private Media media;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    public Borrow(Media media, User user) {
        this.media = media;
        this.user = user;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(media.getBorrowDays());
        this.returned = false;
    }

    public Media getMedia() { 
        return media; 
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

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }

   

    public boolean belongsTo(User u) {
        return this.user.equals(u);
    }
}
