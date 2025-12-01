package library.media;

import java.util.ArrayList;
import java.util.List;

public class MediaManager {

    private List<Media> mediaList;

    public MediaManager() {
        mediaList = new ArrayList<>();
    }

    public void addMedia(Media media) {
        mediaList.add(media);
        System.out.println("Added: " + media.getTitle());
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

    public boolean searchMedia(String keyword) {
        boolean found = false;
        for (Media m : mediaList) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(m);
                found = true;
            }
        }
        return found;
    }

    public Media findMediaById(String id) {
        for (Media m : mediaList) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }
}
