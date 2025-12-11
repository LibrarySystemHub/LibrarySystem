package library.notifications;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.users.User;

class EmailNotifierTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void testNotify() {
        EmailNotifier notifier = new EmailNotifier();
        User user = new User("Alaa", "123", "alaa@mail.com");

        notifier.notify(user, "Your book is overdue!");

        String output = outputStream.toString().trim();

        assertTrue(output.contains("Email sent to Alaa: Your book is overdue!"));
    }
}
