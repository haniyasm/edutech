package student;

import sda.App;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class typeOfResources {

    @FXML
    private Label selectedCourseLabel;

    @FXML
    private ComboBox<String> resourceTypeComboBox;

    // Variable to store the selected course
    private static String selectedCourse;

    // Method to set the selected course (called from the previous screen)
    public static void setSelectedCourse(String course) {
        selectedCourse = course;
    }

    @FXML
    public void initialize() {
        // Display the selected course
        selectedCourseLabel.setText("Selected Course: " + selectedCourse);

        // Populate resource types in the ComboBox
        ObservableList<String> resourceTypes = FXCollections.observableArrayList(
            "Summary Notes",
            "Videos",
            "Reference Books"
        );
        resourceTypeComboBox.setItems(resourceTypes);
    }

    @FXML
    public void handleProceedButton(ActionEvent event) {
        // Get the selected resource type
        String selectedResourceType = resourceTypeComboBox.getValue();

        if (selectedResourceType == null) {
            // Show alert if no resource type is selected
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Resource Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a resource type to proceed.");
            alert.showAndWait();
        } else {
            System.out.println("Proceeding with course: " + selectedCourse + ", Resource Type: " + selectedResourceType);

            try {
                // Navigate to specific screens based on the selected resource type
                if ("Summary Notes".equals(selectedResourceType)) {
                    //Main.setRoot("summaryNotesController"); // Load the controller for Summary Notes
                    summaryNotesController.setSelectedCourse(selectedCourse);
                    App.setRoot("summaryNotesController");
                } 
                else if ("Videos".equals(selectedResourceType)) {
                	videosViewController.setSelectedCourse(selectedCourse);
                    App.setRoot("videosView");
                } 
                else if ("Reference Books".equals(selectedResourceType)) {
                	referenceBooksController.setSelectedCourse(selectedCourse);
                    App.setRoot("referenceBooksView");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
