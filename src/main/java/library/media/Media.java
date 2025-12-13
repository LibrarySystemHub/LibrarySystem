package library.media;
/**
 * Abstract class representing a media item in the library.
 * Subclasses (Book, CD, etc.) must define the type of media and borrowing rules.
 * 
 * @author Jana
 * @version 1.0
 */
public abstract class Media {

    protected String title;
    protected String id;

    /**
     * Constructs a media item with a title and unique identifier.
     * 
     * @param title the title of the media
     * @param id    the unique identifier of the media
     */
    protected Media(String title, String id) {
        this.title = title;
        this.id = id;
    }

    /**
     * Returns the title of the media.
     * 
     * @return the media title
     */
    public String getTitle() { return title; }
    
    /**
     * Returns the unique identifier of the media.
     * 
     * @return the media ID
     */
    public String getId() { return id; }

    /**
     * Returns the number of days the media can be borrowed.
     * Must be implemented by subclasses.
     * 
     * @return the number of borrow days
     */

    public abstract int getBorrowDays();
    
    /**
     * Returns the type of the media (e.g., "BOOK", "CD").
     * Must be implemented by subclasses.
     * 
     * @return the media type
     */
    public abstract String getType();

    /**
     * Returns a string representation of the media item, including type, title, and ID.
     * Example: "BOOK - Clean Code (978)"
     *
     * @return a string describing the media
     */
    @Override
    public String toString() {
        return getType() + " - " + title + " (" + id + ")";
    }
}
