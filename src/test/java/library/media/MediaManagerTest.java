package library.media;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
    @Test
    void testGetAllMedia() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        CD cd = new CD("Best Songs", "CD001");
        manager.addBook(b);
        manager.addCD(cd);

        List<Media> list = manager.getAllMedia();
        assertEquals(2, list.size());
        assertTrue(list.contains(b));
        assertTrue(list.contains(cd));
    }
    @Test
    void testListMediaWhenEmpty() {
        manager.listMedia(); 
    }

    @Test
    void testListMediaWithItems() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        manager.addBook(b);
        manager.listMedia(); 
    }
    @Test
    void testSearchMediaFound() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        manager.addBook(b);
        manager.searchMedia("clean"); 
    }
    @Test
    void testSearchMediaNotFound() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        manager.addBook(b);
        manager.searchMedia("java"); 
    }
    @Test
    void testSearchMediaEmptyList() {
        manager.searchMedia("anything"); 
    }

}
