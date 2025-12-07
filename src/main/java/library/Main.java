package library;

import java.util.ArrayList;
import java.util.Scanner;
import library.admin.Admin;
import library.books.Book;
import library.books.BookManager;
import library.borrow.BorrowManager;
import library.users.User;
import library.users.UserManager;


public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Book> books = StorageManager.loadBooks();
        ArrayList<User> users = StorageManager.loadUsers();
        BookManager manager = new BookManager();
        UserManager userManager = new UserManager(users);
        BorrowManager borrowManager = new BorrowManager(books, users);
        User loggedInUser = null;
        Admin admin = new Admin(
        	    Config.get("ADMIN_USERNAME"),
        	    Config.get("ADMIN_PASSWORD")
        );
        boolean exit = false;

        System.out.println("=== Welcome to the Library System ===");

        while (!exit) {
        	 System.out.println("\nLogin as:");
             System.out.println("1. Admin");
             System.out.println("2. User");
             System.out.println("3. Exit");

            System.out.print("Your choice: ");
            String roleChoice = sc.nextLine();
            
            switch (roleChoice) {
            case "1": 
                System.out.print("Enter admin username: ");
                String adminUser = sc.nextLine();
                System.out.print("Enter admin password: ");
                String adminPass = sc.nextLine();

                admin.login(adminUser, adminPass);
                if (admin.isLoggedIn()) {
                    boolean exitAdminMenu = false;
                    while (!exitAdminMenu) {
                        System.out.println("\n--- Admin Menu ---");
                        System.out.println("1. Add Book");
                        System.out.println("2. List All Books");
                        System.out.println("3. Search Book");
                        System.out.println("4. Check Overdue Books");
                        System.out.println("5. Unregister User");
                        System.out.println("6. Logout");

                        System.out.print("Your choice: ");
                        String choice = sc.nextLine();

                        switch (choice) {
                            case "1":
                                System.out.print("Enter book title: ");
                                String title = sc.nextLine();
                                System.out.print("Enter book author: ");
                                String author = sc.nextLine();
                                System.out.print("Enter book ISBN: ");
                                String isbn = sc.nextLine();
                                manager.addBook(new Book(title, author, isbn));
                                break;

                            case "2":
                                manager.listBooks();
                                break;

                            case "3":
                                System.out.print("Enter keyword to search: ");
                                String keyword = sc.nextLine();
                                manager.searchBook(keyword);
                                break;

                            case "4":
                                borrowManager.checkOverdueBooks();
                                
                                break;

                            case "5":                           	
                            	  System.out.println("Enter username to unregister:");
                            	    String username = sc.nextLine();
                            	    User user = userManager.findUserByName(username);
                            	    if (user != null) {
                            	        userManager.unregisterUser(admin, user ,borrowManager);
                            	       
                            	    } else {
                            	        System.out.println("User not found.");
                            	    }
                                break;

                            case "6":
                                admin.logout();
                                exitAdminMenu = true;
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }
                    }
                } else {
                    System.out.println("Login failed.");
                }
                break;

            case "2":            
            	System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.print("Your choice: ");
                String userOption = sc.nextLine();
                User user = null;
                

                if (userOption.equals("1")) { 
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();
                    System.out.print("Enter your email: ");
                    String email = sc.nextLine();

                    user = new User(name, password, email);
                    userManager.addUser(user);
                    System.out.println("Registration successful!");
                } else if (userOption.equals("2")) { 
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter your password: ");
                    String password = sc.nextLine();

                    user = userManager.findUserByName(name);
                    if (user != null && user.checkPassword(password)) {
                        System.out.println("Login successful!");
                        loggedInUser = user;
                        borrowManager.checkOverdueBooks();
                        
                    } else {
                        System.out.println("Invalid credentials!");
                        break;
                    }
                }else {
                   
                    System.out.println("Invalid choice! Returning to main menu...");
                    break;
                }

                boolean exitUserMenu = false;
                while (!exitUserMenu) {
                    System.out.println("\n--- User Menu ---");
                    System.out.println("1. Search Book");
                    System.out.println("2. Borrow Book");
                    System.out.println("3. Pay Fine");
                    System.out.println("4. List My Borrows");
                    System.out.println("5. Logout");

                    System.out.print("Your choice: ");
                    String choice = sc.nextLine();

                    switch (choice) {
                        case "1":
                            System.out.print("Enter keyword to search: ");
                            String keyword = sc.nextLine();
                            manager.searchBook(keyword);
                            break;

                        case "2":
                            System.out.print("Enter ISBN to borrow: ");
                            String isbnToBorrow = sc.nextLine();
                            Book bookToBorrow = manager.findBookByISBN(isbnToBorrow);
                            if (bookToBorrow != null) {
                                borrowManager.borrowBook(bookToBorrow, user);
                            } else {
                                System.out.println("Book not found!");
                            }
                            break;

                        case "3":
                        	if (loggedInUser.getFineBalance() <= 0) {
                        	    System.out.println("You have no fines to pay!");
                        	    break; 
                        	} else {
                        	   System.out.println("Your current fine: " + loggedInUser.getFineBalance() + " NIS");
                        	}
                            System.out.print("Enter amount to pay: ");
                            double amount = Double.parseDouble(sc.nextLine());                                                    
                            borrowManager.payFine(loggedInUser, amount);                           

                            StorageManager.saveUsers(users);                                                       
                            break;

                        case "4":
                            borrowManager.listBorrows(user);
                            break;

                        case "5":
                            exitUserMenu = true;
                            break;

                        default:
                            System.out.println("Invalid choice!");
                    }
                }
                break;

            case "3":
                exit = true;
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println("Invalid choice!");
        }
    }
        sc.close();
    }
}

