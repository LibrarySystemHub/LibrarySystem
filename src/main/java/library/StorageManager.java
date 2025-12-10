package library;

import library.media.Book;
import library.media.CD;
import library.media.Media;
import library.users.User;
import library.borrow.Borrow;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {

    private static final String MEDIA_FILE = "data/media.txt";
    private static final String USERS_FILE = "data/users.txt";
    private static final String BORROWS_FILE = "data/borrows.txt";

    
    public static ArrayList<Media> loadMedia() {
        ArrayList<Media> list = new ArrayList<>();
        File file = new File(MEDIA_FILE);
        if (!file.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(MEDIA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

                if (parts[0].equals("BOOK")) {
                    list.add(new Book(parts[1], parts[2], parts[3])); // title, author, isbn
                }
                else if (parts[0].equals("CD")) {
                    list.add(new CD(parts[1], parts[2])); // title, serial
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading media: " + e.getMessage());
        }

        return list;
    }

    public static void saveMedia(List<Media> media) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MEDIA_FILE))) {

            for (Media m : media) {
                if (m instanceof Book) {
                    Book b = (Book) m;
                    bw.write("BOOK;" + b.getTitle() + ";" + b.getAuthor() + ";" + b.getId());
                } 
                else if (m instanceof CD) {
                    CD cd = (CD) m;
                    bw.write("CD;" + cd.getTitle() + ";" + cd.getId());
                }
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println("Error saving media: " + e.getMessage());
        }
    }

    

    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
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
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    public static void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {

            for (User u : users) {
                bw.write(u.getName() + ";" + u.getPassword() + ";" + u.getEmail() + ";" + u.getFineBalance());
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }


 
    public static ArrayList<Borrow> loadBorrows(List<Media> media, List<User> users) {

        ArrayList<Borrow> borrows = new ArrayList<>();
        File file = new File(BORROWS_FILE);
        if (!file.exists()) return borrows;

        try (BufferedReader br = new BufferedReader(new FileReader(BORROWS_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(";");

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
            }

        } catch (Exception e) {
            System.out.println("Error loading borrows: " + e.getMessage());
        }

        return borrows;
    }

    public static void saveBorrows(List<Borrow> borrows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BORROWS_FILE))) {

            for (Borrow b : borrows) {
                bw.write(b.getMedia().getId() + ";" +
                         b.getUser().getName() + ";" +
                         b.getBorrowDate() + ";" +
                         b.getDueDate() + ";" +
                         b.isReturned());
                bw.newLine();
            }

        } catch (Exception e) {
            System.out.println("Error saving borrows: " + e.getMessage());
        }
    }
}
