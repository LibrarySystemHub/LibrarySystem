package library;
import java.util.Scanner;
import library.admin.Admin;
import library.books.Book;
import library.books.BookManager;

public class Main {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		BookManager manager = new BookManager();
		Admin admin = new Admin("alaa", "1234");
		boolean exit = false;
		
		 System.out.println("=== Welcome to the Library System ===");

	        while(!exit) {
	            System.out.println("\nChoose an option:");
	            System.out.println("1. Admin Login");
	            System.out.println("2. Admin Logout");
	            System.out.println("3. Add Book (Admin Only)");
	            System.out.println("4. List All Books");
	            System.out.println("5. Search Book (User)");
	            System.out.println("6. Exit");

	            System.out.print("Your choice: ");
	            String choice = sc.nextLine();

	            switch(choice) {
	                case "1": 
	                    System.out.print("Enter username: ");
	                    String username = sc.nextLine();
	                    System.out.print("Enter password: ");
	                    String password = sc.nextLine();
	                    admin.login(username, password);
	                    break;
	                case "2":
	                    admin.logout();
	                    break;
	                case "3":
	                    if(!admin.isLoggedIn()) {
	                        System.out.println("You must be logged in as Admin to add books.");
	                    } else {
	                        System.out.print("Enter book title: ");
	                        String title = sc.nextLine();
	                        System.out.print("Enter book author: ");
	                        String author = sc.nextLine();
	                        System.out.print("Enter book ISBN: ");
	                        String isbn = sc.nextLine();

	                        Book book = new Book(title, author, isbn);
	                        manager.addBook(book);
	                    }
	                    break;
	                case "4":
	                    manager.listBooks();
	                    break;
	                case "5":
	                    System.out.print("Enter keyword to search (title/author/ISBN): ");
	                    String keyword = sc.nextLine();
	                    manager.searchBook(keyword);
	                    break;
	                case "6":
	                    exit = true;
	                    System.out.println("Goodbye!");
	                    break;
	                default:
	                    System.out.println("Invalid choice! Please try again.");
	            }
	        }

	        sc.close();
	    }
		
		
		
}
