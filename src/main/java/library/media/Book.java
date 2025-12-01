package library.media;

import library.media.Media;

public class Book extends Media {

    private String author;

    public Book(String title, String author, String ISBN) {
        super(title, ISBN);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public int getBorrowDays() {
        return 28;
    }

    @Override
    public String getType() {
        return "BOOK";
    }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', ISBN='" + id + "'}";
    }
}
