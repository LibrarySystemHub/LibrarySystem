package library.media;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a collection of media items (Books and CDs).
 * Provides functionality to add, list, search, and find media.
 * 
 * @author Jana
 * @version 1.0
 */
public class MediaManager {

    private List<Media> mediaList;

    /**
     * Constructs a MediaManager with an empty media list.
     */
    public MediaManager() {
        mediaList = new ArrayList<>();
    }

    /**
     * Constructs a MediaManager with a given media list.
     * 
     * @param mediaList initial list of media
     */
    public MediaManager(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    /**
     * Adds a book to the media collection.
     * 
     * @param book the Book to add
     */
    public void addBook(Book book) {
        mediaList.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    /**
     * Adds a CD to the media collection.
     * 
     * @param cd the CD to add
     */
    public void addCD(CD cd) {
        mediaList.add(cd);
        System.out.println("CD added: " + cd.getTitle());
    }

    /**
     * Prints all media in the collection.
     */
    public void listMedia() {
        if (mediaList.isEmpty()) {
            System.out.println("No media available.");
            return;
        }

        for (Media m : mediaList) {
            System.out.println(m);
        }
    }

    /**
     * Searches media titles containing the keyword and prints them.
     * 
     * @param keyword the keyword to search for
     */
    public void searchMedia(String keyword) {
        boolean found = false;

        for (Media m : mediaList) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(m);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching media found.");
        }
    }

    /**
     * Finds a media item by its ID.
     * 
     * @param id the ID to search for
     * @return the Media if found, null otherwise
     */
    public Media findMediaById(String id) {
        for (Media m : mediaList) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Returns the list of all media.
     * 
     * @return the media list
     */
    public List<Media> getAllMedia() {
        return mediaList;
    }
}
