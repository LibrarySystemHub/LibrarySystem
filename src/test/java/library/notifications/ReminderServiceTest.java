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
    private User user2;
    private User user1;

    @BeforeEach
    void setUp() {
        borrowManager = mock(BorrowManager.class);
        reminderService = new ReminderService(borrowManager);
        emailNotifier = mock(EmailNotifier.class);

        reminderService.addObserver(emailNotifier);

        user1 = new User("jana");
        user2 = new User("aya");
    }

    @Test
    void testSendOverdueRemindersSingleUser() {
        when(borrowManager.getAllUsersWithOverdues()).thenReturn(List.of(user1));
        when(borrowManager.countOverduesForUser(user1)).thenReturn(2);

        reminderService.sendOverdueReminders();

        verify(emailNotifier).notify(user1, "You have 2 overdue book(s).");
    }
    @Test
    void testSendOverdueRemindersMultipleUsers() {
        when(borrowManager.getAllUsersWithOverdues()).thenReturn(List.of(user1, user2));
        when(borrowManager.countOverduesForUser(user1)).thenReturn(1);
        when(borrowManager.countOverduesForUser(user2)).thenReturn(3);

        reminderService.sendOverdueReminders();

        verify(emailNotifier).notify(user1, "You have 1 overdue book(s).");
        verify(emailNotifier).notify(user2, "You have 3 overdue book(s).");
    }
    @Test
    void testSendOverdueRemindersNoUser() {
        when(borrowManager.getAllUsersWithOverdues()).thenReturn(List.of());

        reminderService.sendOverdueReminders();

        
        verify(emailNotifier, never()).notify(any(User.class), anyString());
    }
    
    @Test
    void testSendOverdueRemindersMultipleObservers() {
        EmailNotifier emailNotifier2 = mock(EmailNotifier.class);
        reminderService.addObserver(emailNotifier2);

        when(borrowManager.getAllUsersWithOverdues()).thenReturn(List.of(user1));
        when(borrowManager.countOverduesForUser(user1)).thenReturn(2);

        reminderService.sendOverdueReminders();

        verify(emailNotifier).notify(user1, "You have 2 overdue book(s).");
        verify(emailNotifier2).notify(user1, "You have 2 overdue book(s).");
    }
}

