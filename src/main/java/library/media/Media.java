package library.media;

public abstract class Media {

    protected String title;
    protected String id;

    public Media(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() { return title; }
    public String getId() { return id; }

    public abstract int getBorrowDays();
    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " - " + title + " (" + id + ")";
    }
}
