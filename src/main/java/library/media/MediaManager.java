package library.media;

import java.util.ArrayList;
import java.util.List;

public class MediaManager {

    private List<Media> mediaList;

    
    public MediaManager() {
        mediaList = new ArrayList<>();
    }

  
    public MediaManager(List<Media> mediaList) {
        this.mediaList = mediaList;
    }

    
    public void addBook(Book book) {
        mediaList.add(book);
        System.out.println("Book added: " + book.getTitle());
    }


    public void addCD(CD cd) {
        mediaList.add(cd);
        System.out.println("CD added: " + cd.getTitle());
    }


    public void listMedia() {
        if (mediaList.isEmpty()) {
            System.out.println("No media available.");
            return;
        }

        for (Media m : mediaList) {
            System.out.println(m);
        }
    }


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

   
    public Media findMediaById(String id) {
        for (Media m : mediaList) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

   
    public List<Media> getAllMedia() {
        return mediaList;
    }
}
