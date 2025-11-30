package library.books;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookManagerTest {

    private BookManager manager;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		manager = new BookManager();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAddBook() {
		manager.addBook(new Book("Clean Code", "Robert Martin", "9780132350884"));
		assertEquals("Clean Code", manager.findBookByISBN("9780132350884").getTitle());
		
	}
	@Test
	void testSearchBookFound() {
		 manager.addBook(new Book("Clean Code", "Robert Martin", "9780132350884"));
		 assertTrue(manager.searchBook("Clean Code"));
	}
	
	@Test
	void testSearchBookByAuthor() {
	    manager.addBook(new Book("Clean Code", "Robert Martin", "9780132350884"));
	    assertTrue(manager.searchBook("Robert"));
	}
	
	@Test
	void testSearchBookByISBN() {
	    manager.addBook(new Book("Clean Code", "Robert Martin", "9780132350884"));
	    assertTrue(manager.searchBook("9780132350884"), "Book should be found by ISBN");
	}
	
	@Test
	void testSearchBookNotFound() {	   
	    manager.addBook(new Book("Java Basics", "Alaa", "111"));

	    assertFalse(manager.searchBook("Python"));
	}
	
	@Test
	void testSearchBookWhenEmpty() {
	    assertFalse(manager.searchBook("Python"));
	}
	

}
