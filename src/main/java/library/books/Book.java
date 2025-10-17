package library.books;

public class Book {
	
	private String title;
	private String author;
	private String ISBN;
	
	public Book(String title , String author, String ISBN) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
	}
	
	String getTitle() {
		return title;
	}
	
	String getAuthor() {
		return author;
	}

	String getISBN() {
		return ISBN;
	}
	@Override
	public String toString() {
	    return "Book{" +
	           "title='" + title + '\'' +
	           ", author='" + author + '\'' +
	           ", ISBN='" + ISBN + '\'' +
	           '}';
	}

}
