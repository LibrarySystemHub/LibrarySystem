package library;

import java.util.Scanner;

import library.admin.Admin;
import library.media.Book;
import library.media.CD;
import library.media.Media;
import library.media.MediaManager;
import library.borrow.BorrowManager;
import library.users.User;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        MediaManager mediaManager = new MediaManager();
        BorrowManager borrowManager = new BorrowManager();

        Admin admin = new Admin("alaa", "1234");
        User mainUser = new User("jana");

        boolean exit = false;

        System.out.println("=== Welcome to the Library System ===");

        while (!exit) {

            System.out.println("\nChoose an option:");
            System.out.println("1. Admin Login");
            System.out.println("2. Admin Logout");
            System.out.println("3. Add Book (Admin Only)");
            System.out.println("4. Add CD (Admin Only)");
            System.out.println("5. List All Media");
            System.out.println("6. Search Media");
            System.out.println("7. Borrow Media");
            System.out.println("8. Check Overdue Items");
            System.out.println("9. Pay Fine");
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
                        System.out.println(" Admin only!");
                    } else {
                        System.out.print("Book Title: ");
                        String title = sc.nextLine();
                        System.out.print("Author: ");
                        String author = sc.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();

                        mediaManager.addMedia(new Book(title, author, isbn));
                    }
                    break;

                case "4":
                    if (!admin.isLoggedIn()) {
                        System.out.println(" Admin only!");
                    } else {
                        System.out.print("CD Title: ");
                        String cdTitle = sc.nextLine();
                        System.out.print("Serial Number: ");
                        String serial = sc.nextLine();

                        mediaManager.addMedia(new CD(cdTitle, serial));
                    }
                    break;

                case "5":
                    mediaManager.listMedia();
                    break;

                case "6":
                    System.out.print("Keyword: ");
                    mediaManager.searchMedia(sc.nextLine());
                    break;

                case "7":
                    System.out.print("Enter Media ID to borrow: ");
                    Media m = mediaManager.findMediaById(sc.nextLine());

                    if (m != null) {
                        borrowManager.borrowMedia(m, mainUser);
                    } else {
                        System.out.println(" Media not found!");
                    }
                    break;

                case "8":
                    borrowManager.checkOverdue();
                    break;

                case "9":
                    System.out.print("Payment amount: ");
                    double amount = Double.parseDouble(sc.nextLine());
                    mainUser.payFine(amount);
                    break;

                case "10":
                    exit = true;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
