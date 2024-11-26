package courseResources;

public class Book extends Resources {
    private String bookUrl;

    // Constructor to initialize the book's details
    public Book(String title, String description, String courseName, String bookUrl) {
        super(title, description, courseName);
        this.bookUrl = bookUrl;
    }

    // Getter for book URL
    public String getBookUrl() {
        return bookUrl;
    }

    // Setter for book URL
    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    // Override the getResourceType method from Resources
    @Override
    public String getResourceType() {
        return "Book";
    }

    // Override toString to include book-specific details
    @Override
    public String toString() {
        return super.toString() + ", Book URL: " + bookUrl;
    }
}
