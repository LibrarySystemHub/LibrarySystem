package library.notifications;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.borrow.BorrowManager;
import library.users.User;

class ReminderServiceTest {

    private BorrowManager borrowManager;
    private ReminderService reminderService;
    private EmailNotifier emailNotifier;

    private User user;

    @BeforeEach
    void setUp() {
        borrowManager = mock(BorrowManager.class);
        reminderService = new ReminderService(borrowManager);
        emailNotifier = mock(EmailNotifier.class);

        reminderService.addObserver(emailNotifier);

        user = new User("jana");
    }

    @Test
    void testSendOverdueRemindersSendsCorrectMessage() {

        when(borrowManager.getAllUsersWithOverdues()).thenReturn(List.of(user));
        when(borrowManager.countOverduesForUser(user)).thenReturn(2);

        reminderService.sendOverdueReminders();

        verify(emailNotifier).notify(user, "You have 2 overdue book(s).");
    }
}

