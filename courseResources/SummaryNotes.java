package courseResources;

public class SummaryNotes extends Resources {

    private String content;  // Content of the summary notes

    // Constructor
    public SummaryNotes(String title, String description, String courseName, String content) {
        super(title, description, courseName);
        this.content = content;
    }

    // Getter and Setter for content
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // Override the getResourceType method from Resources
    @Override
    public String getResourceType() {
        return "Summary Notes";
    }

    // Override toString to include summary notes-specific details
    @Override
    public String toString() {
        return super.toString() + ", Content: " + content;
    }
}