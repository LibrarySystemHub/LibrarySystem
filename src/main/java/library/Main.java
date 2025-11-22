package library;

import java.util.Scanner;
import library.admin.Admin;
import library.books.Book;
import library.books.BookManager;
import library.borrow.BorrowManager;
import library.users.User;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookManager manager = new BookManager();
        BorrowManager borrowManager = new BorrowManager();
        Admin admin = new Admin("alaa", "1234");
        User user = new User("jana"); // المستخدم الحالي
        boolean exit = false;

        System.out.println("=== Welcome to the Library System ===");

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Admin Login");
            System.out.println("2. Admin Logout");
            System.out.println("3. Add Book (Admin Only)");
            System.out.println("4. List All Books");
            System.out.println("5. Search Book (User)");
            System.out.println("6. Borrow Book");
            System.out.println("7. Check Overdue Books");
            System.out.println("8. Pay Fine");
            System.out.println("9. List All Borrows");
            System.out.println("10. Exit");

            System.out.print("Your choice: ");
            String choice = sc.nextLine();

            switch (choice) {
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
                    if (!admin.isLoggedIn()) {
                        System.out.println("⚠️ You must be logged in as Admin to add books.");
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
                    System.out.print("Enter ISBN to borrow: ");
                    String isbn = sc.nextLine();
                    Book bookToBorrow = manager.findBookByISBN(isbn);
                    if (bookToBorrow != null) {
                        borrowManager.borrowBook(bookToBorrow, user);
                    } else {
                        System.out.println(" Book not found!");
                    }
                    break;

                case "7":
                    borrowManager.checkOverdueBooks();
                    break;

                case "8":
                    System.out.print("Enter amount to pay: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    borrowManager.payFine(user, amount);
                    break;

                case "9":
                    borrowManager.listBorrows();
                    break;

                case "10":
                    exit = true;
                    System.out.println(" Goodbye!");
                    break;

                default:
                    System.out.println(" Invalid choice!");
            }
        }

        sc.close();
    }
}
