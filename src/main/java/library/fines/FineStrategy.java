package library.fines;
/**
 * Interface for fine calculation strategies.
 * Implementing classes define how fines are calculated for overdue items.
 * 
 * @author Jana
 * @version 1.0
 */
public interface FineStrategy {
	 /**
     * Calculates the fine based on the number of overdue days.
     * 
     * @param overdueDays the number of days the item is overdue
     * @return the total fine for the overdue days
     */
    double calculateFine(long overdueDays);
}
