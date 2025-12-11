package library.media;

public class CD extends Media {

    public CD(String title, String serial) {
        super(title, serial);
    }

    @Override
    public int getBorrowDays() {
        return 7;
    }

    @Override
    public String getType() {
        return "CD";
    }
}
