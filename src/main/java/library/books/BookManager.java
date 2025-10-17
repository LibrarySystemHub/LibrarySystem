package library.books;

import java.util.ArrayList;

public class BookManager {

	 private ArrayList<Book> books;
	 
	 public BookManager() {
	        books = new ArrayList<>();
	    }
	 
	 public void addBook(Book book) {
	        books.add(book);
	        System.out.println("Book added: " + book.getTitle());
	    }
	 public void listBooks() {
	        if (books.isEmpty()) {
	            System.out.println("No books available.");
	        } else {
	            System.out.println("List of Books:");
	            for (Book book : books) {
	                System.out.println(book);
	            }
	        }
	  }
	 
	 public void searchBook(String keyword) {
		 boolean found = false;
		 System.out.println("Search results for: \"" + keyword + "\"");
		 for (Book book : books) {
		        if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
		            book.getAuthor().toLowerCase().contains(keyword.toLowerCase()) ||
		            book.getISBN().toLowerCase().contains(keyword.toLowerCase())) {
		            
		            System.out.println(book);
		            found = true;
		        }
		    }
		 if (!found) {
		        System.out.println("No matching books found.");
		    } 
	 }
	 
	
}
