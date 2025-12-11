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

public class StorageManager {

   
    private static Path mediaFile = Path.of("data/media.txt");
    private static Path usersFile = Path.of("data/users.txt");
    private static Path borrowsFile = Path.of("data/borrows.txt");

    
    public static void useCustomPaths(Path media, Path users, Path borrows) {
        mediaFile = media;
        usersFile = users;
        borrowsFile = borrows;
    }

   
    
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
            System.out.println("Error loading media: " + e.getMessage());
        }

        return list;
    }

    
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
            System.out.println("Error saving media: " + e.getMessage());
        }
    }

    
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
            System.out.println("Error loading users: " + e.getMessage());
        }

        return users;
    }

    
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
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    
    public static ArrayList<Borrow> loadBorrows(List<Media> media, List<User> users) {

        ArrayList<Borrow> borrows = new ArrayList<>();

        if (!Files.exists(borrowsFile)) return borrows;

        try (BufferedReader br = Files.newBufferedReader(borrowsFile)) {

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
            System.out.println("Error saving borrows: " + e.getMessage());
        }
    }
}
