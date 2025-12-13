package library.main;

import static org.junit.jupiter.api.Assertions.*;

import library.media.Book;
import library.media.CD;
import library.media.Media;
import library.users.User;
import library.StorageManager;
import library.borrow.Borrow;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.*;

class StorageManagerTest {

    private Path mediaFile;
    private Path usersFile;
    private Path borrowsFile;

    @BeforeEach
    void setup() throws IOException {
        mediaFile = Files.createTempFile("mediaTest", ".txt");
        usersFile = Files.createTempFile("usersTest", ".txt");
        borrowsFile = Files.createTempFile("borrowsTest", ".txt");

        StorageManager.useCustomPaths(mediaFile, usersFile, borrowsFile);
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(mediaFile);
        Files.deleteIfExists(usersFile);
        Files.deleteIfExists(borrowsFile);
    }

    

    @Test
    void testSaveAndLoadMedia() throws IOException {
        List<Media> list = new ArrayList<>();
        list.add(new Book("Clean Code", "Robert Martin", "978"));
        list.add(new CD("Best Songs", "CD1"));

        StorageManager.saveMedia(list);

        List<Media> loaded = StorageManager.loadMedia();

        assertEquals(2, loaded.size());
        assertEquals("Clean Code", loaded.get(0).getTitle());
        assertEquals("CD1", loaded.get(1).getId());
    }

    @Test
    void testLoadMediaWhenEmpty() {
        List<Media> loaded = StorageManager.loadMedia();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void testLoadMediaWithInvalidLines() throws IOException {
        Files.writeString(mediaFile, "INVALID;LINE\nBOOK;T;A;ID123");

        List<Media> loaded = StorageManager.loadMedia();

        assertEquals(1, loaded.size());
        assertEquals("T", loaded.get(0).getTitle());
    }

    

    @Test
    void testSaveAndLoadUsers() {
        List<User> users = new ArrayList<>();
        User u = new User("alaa", "1234", "a@mail.com");
        u.addFine(10);
        users.add(u);

        StorageManager.saveUsers(users);

        List<User> loaded = StorageManager.loadUsers();

        assertEquals(1, loaded.size());
        assertEquals("alaa", loaded.get(0).getName());
        assertEquals(10, loaded.get(0).getFineBalance());
    }

    @Test
    void testLoadUsersWhenEmpty() {
        List<User> users = StorageManager.loadUsers();
        assertTrue(users.isEmpty());
    }

   

    @Test
    void testSaveAndLoadBorrows() {
        Media b = new Book("Clean Code", "Martin", "978");
        User u = new User("jana", "123", "mail");

        Borrow borrow = new Borrow(b, u);
        borrow.setBorrowDate(LocalDate.of(2024, 1, 1));
        borrow.setDueDate(LocalDate.of(2024, 1, 10));
        borrow.setReturned(false);

        List<Borrow> list = new ArrayList<>();
        list.add(borrow);

        StorageManager.saveBorrows(list);

        List<Borrow> loaded = StorageManager.loadBorrows(
                List.of(b),
                List.of(u)
        );

        assertEquals(1, loaded.size());
        assertEquals("jana", loaded.get(0).getUser().getName());
        assertEquals(LocalDate.of(2024, 1, 10), loaded.get(0).getDueDate());
    }

    @Test
    void testLoadBorrowsWhenEmpty() {
        List<Borrow> borrows = StorageManager.loadBorrows(new ArrayList<>(), new ArrayList<>());
        assertTrue(borrows.isEmpty());
    }

    @Test
    void testLoadBorrowsIgnoresInvalidLines() throws IOException {
        Files.writeString(borrowsFile, "INVALID;DATA\n978;jana;2024-01-01;2024-01-10;false");

        Media m = new Book("Clean Code", "A", "978");
        User u = new User("jana", "p", "e");
        u.addFine(0);
        List<Borrow> list = StorageManager.loadBorrows(List.of(m), List.of(u));

        assertEquals(1, list.size()); // Only valid entry is loaded
    }

    @Test
    void testLoadBorrowsSkipsMissingMediaOrUser() throws IOException {
        Files.writeString(borrowsFile, "999;unknown;2024-01-01;2024-01-10;false");

        List<Borrow> list = StorageManager.loadBorrows(new ArrayList<>(), new ArrayList<>());

        assertTrue(list.isEmpty());
    }

    

    @Test
    void testUseCustomPathsChangesFiles() throws IOException {
        Path newMedia = Files.createTempFile("newMedia", ".txt");
        Path newUsers = Files.createTempFile("newUsers", ".txt");
        Path newBorrows = Files.createTempFile("newBorrows", ".txt");

        StorageManager.useCustomPaths(newMedia, newUsers, newBorrows);

        Files.writeString(newMedia, "BOOK;T;A;ID");

        List<Media> loaded = StorageManager.loadMedia();
        assertEquals(1, loaded.size());
    }
      @Test
    void testLoadMediaFileNotExist() throws IOException {
        Files.deleteIfExists(mediaFile);
        List<Media> loaded = StorageManager.loadMedia();
        assertTrue(loaded.isEmpty());
    }

    @Test
    void testSaveMediaInvalidPath() throws IOException {
        Path invalidPath = Path.of("/invalid/path/media.txt");
        StorageManager.useCustomPaths(invalidPath, usersFile, borrowsFile);
        List<Media> mediaList = List.of(new Book("T", "A", "ID"));
        assertDoesNotThrow(() -> StorageManager.saveMedia(mediaList));
    }

 @Test
void testLoadUsersInvalidLine() throws IOException {
    Files.writeString(usersFile, "invalid;line\nalaa;1234;a@mail.com;10");
    List<User> loaded = StorageManager.loadUsers();
    assertEquals(1, loaded.size());
    assertEquals("alaa", loaded.get(0).getName());
}
}
