package courseResources;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import courses.Course;

import java.io.IOException;

import application.Main;

public class summaryController {

    private String resourceType;  // The resource type (Summary)
    private static String selectedCourseName; // The selected course name

    @FXML private TextField summaryTitleField;  // Text field for the summary title
    @FXML private TextArea summaryTextField;   // Text area for summary content
    @FXML private Button addSummaryButton;     // Button for adding the summary
    @FXML private Button backButton;           // Button for going back

    // Setter methods to pass courses and resource type
    public void setCourseName(String courseName) {
        selectedCourseName = courseName; // Store the selected course name
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType; // Set resource type (if needed)
    }

    @FXML
    private void handleAddSummary() throws IOException {
        String summaryTitle = summaryTitleField.getText();
        String summaryContent = summaryTextField.getText();

        // Validate inputs
        if (summaryTitle.isEmpty() || summaryContent.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in the summary title and content.");
            alert.showAndWait();
        } else {
            // Search for the course by name from the global courses list in Main
            Course selectedCourse = findCourseByName(selectedCourseName);
            
            if (selectedCourse != null) {
                // Add the summary to the found course using the addSummary method
                selectedCourse.addSummaryNotes(summaryTitle, summaryContent, selectedCourseName);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Summary Added");
                alert.setHeaderText("Success");
                alert.setContentText("The summary '" + summaryTitle + "' has been added to the course '" + selectedCourseName + "'.");
                alert.showAndWait();
            } else {
                // If no course with the provided name is found
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Course Not Found");
                alert.setHeaderText("Error");
                alert.setContentText("No course found with the name '" + selectedCourseName + "'. Please check the course name.");
                alert.showAndWait();
            }
        }
        
        Main.setRoot("usertype");
    }

    // Helper method to find a course by its name from Main's courses list
    private Course findCourseByName(String courseName) {
        // Access the courses list from Main and search for the course by name
        for (Course course : Main.courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;  // Return the course if it matches the name
            }
        }
        return null;  // Return null if no course is found with the given name
    }

    // Method to handle Back button click (similar to bookController's back button)
    @FXML
    private void handleBackButton() {
        System.out.println("Back button clicked!");
    }

    // Method to show an alert with a custom message (similar to bookController)
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText("Incomplete Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}