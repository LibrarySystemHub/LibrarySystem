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
    void testAddBook() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        manager.addBook(b);

        Media found = manager.findMediaById("9780132350884");
        assertNotNull(found);
        assertEquals("Clean Code", found.getTitle());
    }

    @Test
    void testAddCD() {
        CD cd = new CD("Best Songs", "CD001");
        manager.addCD(cd);

        Media found = manager.findMediaById("CD001");
        assertNotNull(found);
        assertEquals("Best Songs", found.getTitle());
    }

    @Test
    void testSearchMediaByTitle() {
        manager.addBook(new Book("Clean Code", "Robert Martin", "9780132350884"));

     
        Media found = manager.findMediaById("9780132350884");
        assertNotNull(found);
    }

    @Test
    void testSearchMediaByID() {
        manager.addBook(new Book("Clean Code", "Robert Martin", "9780132350884"));

        Media found = manager.findMediaById("9780132350884");
        assertNotNull(found);
    }

    @Test
    void testSearchWhenNotFound() {
        manager.addBook(new Book("Java Basics", "Alaa", "111"));

        Media found = manager.findMediaById("999");
        assertNull(found);
    }

    @Test
    void testSearchWhenEmpty() {
        Media found = manager.findMediaById("any");
        assertNull(found);
    }
}
