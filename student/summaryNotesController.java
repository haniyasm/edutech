package student;

import sda.App;
import courseResources.Resources;
import courseResources.SummaryNotes;
import courses.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class summaryNotesController {
     @FXML
    private Label courseNameLabel;

    @FXML
    private VBox summaryContainer; // VBox to hold multiple summaries dynamically

    private static String selectedCourse;

    public static void setSelectedCourse(String course) {
        selectedCourse = course;
    }

    @FXML
    public void initialize() {
        // Set the course name in the label
        courseNameLabel.setText("Course: " + selectedCourse);

        // Clear the VBox to avoid duplicate entries if reinitialized
        summaryContainer.getChildren().clear();

        // Find the selected course from the courses list
        Course course = App.courses.stream()
                .filter(c -> c.getCourseName().equalsIgnoreCase(selectedCourse))
                .findFirst()
                .orElse(null);

        if (course != null) {
            // Iterate through the resources and display summaries
            for (Resources resource : course.getResources()) {
                if (resource instanceof SummaryNotes) {
                    SummaryNotes summary = (SummaryNotes) resource;

                    // Create a new label for the title
                    Label titleLabel = new Label("Title: " + summary.getTitle());
                    titleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 5;");

                    // Create a new label for the content
                    Label contentLabel = new Label(summary.getContent());
                    contentLabel.setWrapText(true);
                    contentLabel.setStyle("-fx-font-size: 14; -fx-padding: 10; -fx-background-color: #f9f9f9; -fx-border-color: #ccc;");

                    // Add both labels to the VBox
                    summaryContainer.getChildren().addAll(titleLabel, contentLabel);
                }
            }
        } else {
            // Display a message if no course is found
            Label noCourseLabel = new Label("No summaries found for this course.");
            summaryContainer.getChildren().add(noCourseLabel);
        }
    }
    
}
