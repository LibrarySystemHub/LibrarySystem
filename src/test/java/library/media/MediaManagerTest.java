package library.media;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MediaManagerTest {

    private MediaManager manager;

    @BeforeEach
    void setUp() throws Exception {
        manager = new MediaManager();
    }

    @Test
    void testAddMediaBook() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        manager.addMedia(b);

        Media found = manager.findMediaById("9780132350884");

        assertNotNull(found);
        assertEquals("Clean Code", found.getTitle());
    }

    @Test
    void testSearchMediaByTitle() {
        manager.addMedia(new Book("Clean Code", "Robert Martin", "9780132350884"));
        assertTrue(manager.searchMedia("Clean Code"));
    }

    @Test
    void testSearchMediaByAuthor() {
        manager.addMedia(new Book("Clean Code", "Robert Martin", "9780132350884"));
        assertTrue(manager.searchMedia("Robert"));
    }

    @Test
    void testSearchMediaByISBN() {
        manager.addMedia(new Book("Clean Code", "Robert Martin", "9780132350884"));
        assertNotNull(manager.findMediaById("9780132350884"));
    }

    @Test
    void testSearchWhenNotFound() {
        manager.addMedia(new Book("Java Basics", "Alaa", "111"));
        assertFalse(manager.searchMedia("Python"));
    }

    @Test
    void testSearchWhenEmpty() {
        assertFalse(manager.searchMedia("Anything"));
    }
}
