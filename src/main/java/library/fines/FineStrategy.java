package library.fines;

public interface FineStrategy {
    double calculateFine(long overdueDays);
}
