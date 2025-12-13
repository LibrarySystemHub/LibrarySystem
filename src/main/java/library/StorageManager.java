package library;

import library.media.Book;
import library.media.CD;
import library.media.Media;
import library.users.User;
import library.borrow.Borrow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
/**
 * Handles storage and retrieval of media, users, and borrow records
 * to/from text files.
 * Provides methods to load and save data for persistence.
 * 
 * @author Alaa
 * @author jana
 * @version 1.0
 */
public class StorageManager {

   private static final Logger logger = Logger.getLogger(StorageManager.class.getName());
    private static Path mediaFile = Path.of("data/media.txt");
    private static Path usersFile = Path.of("data/users.txt");
    private static Path borrowsFile = Path.of("data/borrows.txt");

    /**
     * Allows overriding default file paths for testing or custom storage.
     * 
     * @param media Path to media file
     * @param users Path to users file
     * @param borrows Path to borrows file
     */
    public static void useCustomPaths(Path media, Path users, Path borrows) {
        mediaFile = media;
        usersFile = users;
        borrowsFile = borrows;
    }

   
    /** 
     * Loads all media records from the media file.
     * 
     * @return list of Media objects
     */
    public static ArrayList<Media> loadMedia() {
        ArrayList<Media> list = new ArrayList<>();

        if (!Files.exists(mediaFile)) return list;

        try (BufferedReader br = Files.newBufferedReader(mediaFile)) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                if (parts[0].equals("BOOK")) {
                    list.add(new Book(parts[1], parts[2], parts[3]));
                }
                else if (parts[0].equals("CD")) {
                    list.add(new CD(parts[1], parts[2]));
                }
            }

        } catch (Exception e) {
            logger.severe("Error loading media: " + e.getMessage());
        }

        return list;
    }

    /** 
     * Saves a list of media records to the media file.
     * 
     * @param media list of Media objects to save
     */
    public static void saveMedia(List<Media> media) {
        try (BufferedWriter bw = Files.newBufferedWriter(mediaFile)) {

            for (Media m : media) {

                if (m instanceof Book b) {
                    bw.write("BOOK;" + b.getTitle() + ";" + b.getAuthor() + ";" + b.getId());
                }
                else if (m instanceof CD cd) {
                    bw.write("CD;" + cd.getTitle() + ";" + cd.getId());
                }

                bw.newLine();
            }

        } catch (Exception e) {
            logger.severe("Error saving media: " + e.getMessage());
        }
    }

    /**
     * Loads all users from the users file.
     * 
     * @return list of User objects
     */
    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();

        if (!Files.exists(usersFile)) return users;

        try (BufferedReader br = Files.newBufferedReader(usersFile)) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                String name = parts[0];
                String pass = parts[1];
                String email = parts[2];
                double fine = Double.parseDouble(parts[3]);

                User user = new User(name, pass, email);
                user.addFine(fine);

                users.add(user);
            }

        } catch (Exception e) {
            logger.severe("Error loading users: " + e.getMessage());
        }

        return users;
    }

    /**
     * Saves a list of users to the users file.
     * 
     * @param users list of User objects to save
     */
    public static void saveUsers(List<User> users) {
        try (BufferedWriter bw = Files.newBufferedWriter(usersFile)) {

            for (User u : users) {
                bw.write(u.getName() + ";" +
                        u.getPassword() + ";" +
                        u.getEmail() + ";" +
                        u.getFineBalance());
                bw.newLine();
            }

        } catch (Exception e) {
            logger.severe("Error saving users: " + e.getMessage());
        }
    }


    /**
     * Loads borrow records from the borrows file, linking to existing users and media.
     * 
     * @param media list of Media objects to match
     * @param users list of User objects to match
     * @return list of Borrow objects
     */
    public static ArrayList<Borrow> loadBorrows(List<Media> media, List<User> users) {

        ArrayList<Borrow> borrows = new ArrayList<>();

        if (!Files.exists(borrowsFile)) return borrows;

        try (BufferedReader br = Files.newBufferedReader(borrowsFile)) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                if (parts.length < 5) continue;
                try {
                String mediaId = parts[0];
                String username = parts[1];
                LocalDate borrowDate = LocalDate.parse(parts[2]);
                LocalDate dueDate = LocalDate.parse(parts[3]);
                boolean returned = Boolean.parseBoolean(parts[4]);

                Media m = media.stream().filter(x -> x.getId().equals(mediaId)).findFirst().orElse(null);
                User u = users.stream().filter(x -> x.getName().equals(username)).findFirst().orElse(null);

                if (m != null && u != null) {
                    Borrow b = new Borrow(m, u);
                    b.setBorrowDate(borrowDate);
                    b.setDueDate(dueDate);
                    b.setReturned(returned);
                    borrows.add(b);
                }
                } catch (Exception e) {
                   
                    continue;
                }
            }

        } catch (Exception e) {
            logger.severe("Error loading borrows: " + e.getMessage());
        }

        return borrows;
    }

    /**
     * Saves a list of borrows to the borrows file.
     * 
     * @param borrows list of Borrow objects to save
     */
    public static void saveBorrows(List<Borrow> borrows) {
        try (BufferedWriter bw = Files.newBufferedWriter(borrowsFile)) {

            for (Borrow b : borrows) {
                bw.write(b.getMedia().getId() + ";" +
                        b.getUser().getName() + ";" +
                        b.getBorrowDate() + ";" +
                        b.getDueDate() + ";" +
                        b.isReturned());
                bw.newLine();
            }

        } catch (Exception e) {
            logger.severe("Error saving borrows: " + e.getMessage());
        }
    }
}
