package courseResources;

public abstract class Resources {
    private String title;
    private String description;
    private String courseName;

    // Constructor
    public Resources(String title, String description, String courseName) {
        this.title = title;
        this.description = description;
        this.courseName = courseName;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    // Abstract method to get the resource type (to be implemented by subclasses)
    public abstract String getResourceType();

    // Method to associate a resource with a course
    public void addCourse(String courseName) {
        // Check if the course name is valid
        if (courseName == null || courseName.trim().isEmpty()) {
            throw new IllegalArgumentException("Course name cannot be null or empty.");
        }
        // Set the course name for this resource
        setCourseName(courseName);
        System.out.println("Resource titled '" + title + "' has been assigned to course: " + courseName);
    }

    // Common method to display resource details
    @Override
    public String toString() {
        return "Resource Type: " + getResourceType() + 
               ", Title: " + title + 
               ", Description: " + description + 
               ", Course Name: " + courseName;
    }
}