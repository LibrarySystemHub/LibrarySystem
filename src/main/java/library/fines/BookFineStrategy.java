package library.fines;
/**
 * Calculates fines for overdue books.
 * Each overdue day incurs a fixed fine of 10 units.
 * 
 * @author Jana
 * @version 1.0
 */
public class BookFineStrategy implements FineStrategy {
	/**
     * Calculates the fine based on the number of overdue days.
     * 
     * @param overdueDays the number of days the book is overdue
     * @return the total fine for the overdue days
     */
    @Override
    public double calculateFine(long overdueDays) {
    	 if (overdueDays <= 0) {
             return 0.0;  
         }
         return overdueDays * 10;
     
    }
}
