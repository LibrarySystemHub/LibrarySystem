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

        MediaManager manager = new MediaManager();
        BorrowManager borrowManager = new BorrowManager();

        Admin admin = new Admin("alaa", "1234");
        User user = new User("jana");

        boolean exit = false;

        while (!exit) {
            System.out.println("\n1. Admin Login\n2. Admin Logout\n3. Add Book\n4. Add CD\n5. List Media\n6. Search Media\n7. Borrow Media\n8. Check Overdue\n9. Pay Fine\n10. Exit");
            System.out.print("Your choice: ");
            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("Username: ");
                    String u = sc.nextLine();
                    System.out.print("Password: ");
                    String p = sc.nextLine();
                    admin.login(u, p);
                    break;

                case "2":
                    admin.logout();
                    break;

                case "3":
                    if (!admin.isLoggedIn()) {
                        System.out.println("Admin only.");
                    } else {
                        System.out.print("Title: ");
                        String t = sc.nextLine();
                        System.out.print("Author: ");
                        String a = sc.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();
                        manager.addMedia(new Book(t, a, isbn));
                    }
                    break;

                case "4":
                    if (!admin.isLoggedIn()) {
                        System.out.println("Admin only.");
                    } else {
                        System.out.print("CD title: ");
                        String ct = sc.nextLine();
                        System.out.print("Serial: ");
                        String cs = sc.nextLine();
                        manager.addMedia(new CD(ct, cs));
                    }
                    break;

                case "5":
                    manager.listMedia();
                    break;

                case "6":
                    System.out.print("Keyword: ");
                    manager.searchMedia(sc.nextLine());
                    break;

                case "7":
                    System.out.print("Enter media ID: ");
                    Media m = manager.findMediaById(sc.nextLine());
                    if (m != null) {
                        borrowManager.borrowMedia(m, user);
                    } else {
                        System.out.println("Not found.");
                    }
                    break;

                case "8":
                    borrowManager.checkOverdue();
                    break;

                case "9":
                    System.out.print("Amount: ");
                    user.payFine(Double.parseDouble(sc.nextLine()));
                    break;

                case "10":
                    exit = true;
                    break;
            }
        }

        sc.close();
    }
}
