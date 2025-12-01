package library.fines;

public class CDFineStrategy implements FineStrategy {

    @Override
    public double calculateFine(long overdueDays) {
        return overdueDays * 20; 
    }
}


