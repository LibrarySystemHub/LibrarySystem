package library.media;

import library.media.Media;

/**
 * Represents a book in the library.
 * Extends the Media class and provides details specific to books.
 * 
 * @author Alaa
 * @version 1.0
 */
public class Book extends Media {

    private String author;

    /**
     * Constructs a Book with a title, author, and ISBN.
     * 
     * @param title  the title of the book
     * @param author the author of the book
     * @param ISBN   the ISBN of the book
     */
    public Book(String title, String author, String ISBN) {
        super(title, ISBN);
        this.author = author;
    }

    /**
     * Returns the author of the book.
     * 
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the number of days the book can be borrowed.
     * 
     * @return number of borrow days (28 for books)
     */
    @Override
    public int getBorrowDays() {
        return 28;
    }

    /**
     * Returns the type of media.
     * 
     * @return "BOOK" for this class
     */
    @Override
    public String getType() {
        return "BOOK";
    }

    /**
     * Returns a string representation of the book.
     * 
     * @return string with title, author, and ISBN
     */
    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', ISBN='" + id + "'}";
    }
}
