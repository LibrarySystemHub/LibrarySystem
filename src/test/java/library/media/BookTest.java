package library.media;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void testBookConstructorAndGetters() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");

        assertEquals("Clean Code", b.getTitle());
        assertEquals("Robert Martin", b.getAuthor());
        assertEquals("9780132350884", b.getId());    // id inherited from Media
        assertEquals("BOOK", b.getType());
        assertEquals(28, b.getBorrowDays());
    }

    @Test
    void testToStringFormat() {
        Book b = new Book("Java", "Alaa", "111");
        String text = b.toString();

        assertTrue(text.contains("title='Java'"));
        assertTrue(text.contains("author='Alaa'"));
        assertTrue(text.contains("ISBN='111'"));
    }
    @Test
    void testToStringBook() {
        Book b = new Book("Clean Code", "Robert Martin", "9780132350884");
        String result = b.toString();
        assertTrue(result.contains("Clean Code"));
        assertTrue(result.contains("9780132350884"));
    }

}
