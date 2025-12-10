package library;

import java.util.ArrayList;
import java.util.Scanner;

import library.admin.Admin;
import library.media.Book;
import library.media.CD;
import library.media.Media;
import library.media.MediaManager;
import library.borrow.BorrowManager;
import library.users.User;
import library.users.UserManager;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

       
        ArrayList<Media> mediaList = StorageManager.loadMedia();
        ArrayList<User> users = StorageManager.loadUsers();

        MediaManager mediaManager = new MediaManager(mediaList);
        BorrowManager borrowManager = new BorrowManager();
        UserManager userManager = new UserManager(users);

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

            String role = sc.nextLine();

            switch (role) {

            
            case "1":
                System.out.print("Admin Username: ");
                String aUser = sc.nextLine();
                System.out.print("Admin Password: ");
                String aPass = sc.nextLine();

                admin.login(aUser, aPass);

                if (!admin.isLoggedIn()) {
                    System.out.println("❌ Invalid admin credentials");
                    break;
                }

                boolean adminExit = false;
                while (!adminExit) {

                    System.out.println("\n--- ADMIN MENU ---");
                    System.out.println("1. Add Book");
                    System.out.println("2. Add CD");
                    System.out.println("3. List Media");
                    System.out.println("4. Search Media");
                    System.out.println("5. Check Overdue Items");
                    System.out.println("6. Unregister User");
                    System.out.println("7. Logout");
                    System.out.print("Your choice: ");

                    String ch = sc.nextLine();

                    switch (ch) {

                        case "1":
                            System.out.print("Book Title: ");
                            String title = sc.nextLine();
                            System.out.print("Author: ");
                            String author = sc.nextLine();
                            System.out.print("ISBN: ");
                            String isbn = sc.nextLine();

                            mediaManager.addBook(new Book(title, author, isbn));

                           
                            StorageManager.saveMedia(mediaManager.getAllMedia());
                            break;

                        case "2":
                            System.out.print("CD Title: ");
                            String cdTitle = sc.nextLine();
                            System.out.print("Serial: ");
                            String serial = sc.nextLine();

                            mediaManager.addCD(new CD(cdTitle, serial));
                            StorageManager.saveMedia(mediaManager.getAllMedia());
                            break;

                        case "3":
                            mediaManager.listMedia();
                            break;

                        case "4":
                            System.out.print("Keyword: ");
                            mediaManager.searchMedia(sc.nextLine());
                            break;

                        case "5":
                            borrowManager.checkOverdue();
                            break;

                        case "6":
                            System.out.print("Username to remove: ");
                            String rmName = sc.nextLine();
                            User toRemove = userManager.findUserByName(rmName);

                            if (toRemove != null) {
                                userManager.unregisterUser(admin, toRemove, borrowManager);
                                StorageManager.saveUsers(userManager.getUsers());
                            } else {
                                System.out.println("User not found.");
                            }
                            break;

                        case "7":
                            admin.logout();
                            adminExit = true;
                            break;

                        default:
                            System.out.println("Invalid choice!");
                    }
                }
                break;

         
            case "2":

                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.print("Your choice: ");

                String opt = sc.nextLine();

                if (opt.equals("1")) {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Password: ");
                    String pass = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    User newUser = new User(name, pass, email);
                    userManager.addUser(newUser);
                    StorageManager.saveUsers(userManager.getUsers());

                    System.out.println("Registration successful!");

                } else if (opt.equals("2")) {

                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Password: ");
                    String pass = sc.nextLine();

                    loggedInUser = userManager.findUserByName(name);

                    if (loggedInUser == null || !loggedInUser.checkPassword(pass)) {
                        System.out.println(" Invalid login.");
                        break;
                    }

                    boolean userExit = false;

                    while (!userExit) {

                        System.out.println("\n--- USER MENU ---");
                        System.out.println("1. Search Media");
                        System.out.println("2. Borrow Media");
                        System.out.println("3. Pay Fine");
                        System.out.println("4. List My Borrows");
                        System.out.println("5. Logout");
                        System.out.print("Your choice: ");

                        String choice = sc.nextLine();

                        switch (choice) {

                            case "1":
                                System.out.print("Keyword: ");
                                mediaManager.searchMedia(sc.nextLine());
                                break;

                            case "2":
                                System.out.print("Enter Media ID: ");
                                String id = sc.nextLine();

                                // FIX: rename variable to avoid conflict
                                Media mediaItem = mediaManager.findMediaById(id);

                                if (mediaItem != null) {
                                    borrowManager.borrowMedia(mediaItem, loggedInUser);
                                } else {
                                    System.out.println("Media not found.");
                                }
                                break;

                            case "3":
                                System.out.println("Your fine: " + loggedInUser.getFineBalance());
                                System.out.print("Enter amount: ");
                                double amt = Double.parseDouble(sc.nextLine());
                                borrowManager.payFine(loggedInUser, amt);
                                StorageManager.saveUsers(userManager.getUsers());
                                break;

                            case "4":
                                borrowManager.listBorrows();
                                break;

                            case "5":
                                userExit = true;
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }
                    }
                }
                break;

            case "3":
                exit = true;
                System.out.println("Goodbye!");
                break;

            default:
                System.out.println("Invalid option.");
            }
        }

        sc.close();
    }
}
