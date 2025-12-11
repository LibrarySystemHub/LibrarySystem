package library.media;
/**
 * Represents a CD in the library.
 * Extends the Media class and provides details specific to CDs.
 * 
 * @author Jana
 * @version 1.0
 */
public class CD extends Media {

	  /**
     * Constructs a CD with a title and serial number.
     * 
     * @param title  the title of the CD
     * @param serial the serial number of the CD
     */
    public CD(String title, String serial) {
        super(title, serial);
    }

    /**
     * Returns the number of days the CD can be borrowed.
     * 
     * @return number of borrow days (7 for CDs)
     */
    @Override
    public int getBorrowDays() {
        return 7;
    }

    /**
     * Returns the type of media.
     * 
     * @return "CD" for this class
     */
    @Override
    public String getType() {
        return "CD";
    }
}
