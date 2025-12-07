package library;
import library.books.Book;
import library.users.User;
import library.borrow.Borrow;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
	private static final String BOOKS_FILE = "data/books.txt";
    private static final String USERS_FILE = "data/users.txt";
    private static final String BORROWS_FILE = "data/borrows.txt";
    
    public static ArrayList<Book> loadBooks() {
        ArrayList<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) {
            return books;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 3) {
                    books.add(new Book(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }
    public static void saveBooks(ArrayList<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKS_FILE , false))) {
            for (Book book : books) {
                bw.write(book.getTitle() + ";" + book.getAuthor() + ";" + book.getISBN());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
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
                if (parts.length == 4) { 
                    String name = parts[0];
                    String password = parts[1];
                    String email = parts[2];
                    double fine = Double.parseDouble(parts[3]);

                    User user = new User(name, password, email);
                    user.addFine(fine);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            System.out.println("Users file not found. Starting with empty list.");
        }
        return users;
    }
    
    public static void saveUsers(List<User> users) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                bw.write(u.getName() + ";" + u.getPassword() + ";" + u.getEmail() + ";" + u.getFineBalance());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Borrow> loadBorrows(List<Book> books, List<User> users) {
        ArrayList<Borrow> borrows = new ArrayList<>();
        File file = new File(BORROWS_FILE);
        if (!file.exists()) return borrows;

        try (BufferedReader br = new BufferedReader(new FileReader(BORROWS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 5) {
                    String isbn = parts[0];
                    String username = parts[1];
                    LocalDate borrowDate = LocalDate.parse(parts[2]);
                    LocalDate dueDate = LocalDate.parse(parts[3]);
                    boolean returned = Boolean.parseBoolean(parts[4]);

                    Book book = books.stream()
                                     .filter(b -> b.getISBN().equals(isbn))
                                     .findFirst().orElse(null);
                    User user = users.stream()
                                     .filter(u -> u.getName().equals(username))
                                     .findFirst().orElse(null);

                    if (book != null && user != null) {
                        Borrow borrow = new Borrow(book, user);
                        borrow.setBorrowDate(borrowDate);
                        borrow.setDueDate(dueDate);
                        borrow.setReturned(returned);
                        borrows.add(borrow);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading borrows: " + e.getMessage());
        }

        return borrows;
    }
    public static void saveBorrows(List<Borrow> borrows) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BORROWS_FILE))) {
            for (Borrow b : borrows) {
                bw.write(b.getBook().getISBN() + ";" +
                         b.getUser().getName() + ";" +
                         b.getBorrowDate() + ";" +
                         b.getDueDate() + ";" +
                         b.isReturned());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving borrows: " + e.getMessage());
        }
    }

}
