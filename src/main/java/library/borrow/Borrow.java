package library.borrow;

import java.time.LocalDate;

import library.media.Media;
import library.users.User;
/**
 * Represents a borrowing record of a media item by a user.
 * Tracks the borrow date, due date, and return status.
 * 
 * @author Alaa
 * @author Jana
 * @version 1.0
 */
public class Borrow {

    private Media media;
    private User user;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private boolean returned;

    /**
     * Creates a borrowing record for a media item and user.
     * The borrow date is set to the current date, and the due date
     * is calculated based on the media's borrow period.
     * 
     * @param media the media being borrowed
     * @param user the user borrowing the media
     */
    public Borrow(Media media, User user) {
        this.media = media;
        this.user = user;
        this.borrowDate = LocalDate.now();
        this.dueDate = borrowDate.plusDays(media.getBorrowDays());
        this.returned = false;
    }

    /** @return the borrowed media */
    public Media getMedia() { return media; }
    
    /** @return the user who borrowed the media */
    public User getUser() { return user; }
    
    /** @return the date when the media was borrowed */
    public LocalDate getBorrowDate() { return borrowDate; }
    
    /** @return the due date for returning the media */
    public LocalDate getDueDate() { return dueDate; }
    
    /** @return true if the media has been returned, false otherwise */
    public boolean isReturned() { return returned; }

    /**
     * Sets the returned status of the borrowed media.
     * 
     * @param returned true if returned, false otherwise
     */
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    /**
     * Sets the borrow date.
     * 
     * @param borrowDate the new borrow date
     */
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    /**
     * Sets the due date.
     * 
     * @param dueDate the new due date
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Checks if the borrowed media is overdue.
     * 
     * @return true if not returned and the current date is after due date, false otherwise
     */
    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(dueDate);
    }
}
