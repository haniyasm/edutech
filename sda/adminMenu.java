package sda;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import courses.CourseController;

public class adminMenu {
    @FXML
    private Button addCourseButton;
    @FXML
    private Button addCourseResourcesButton;
    @FXML
    private Button createQuizButton;
    @FXML
    private Button createAssignmentsButton;
    @FXML
    private Button manageStudentProfileButton;
    @FXML
    private Button logoutButton;

    // Action to add a new course (method is not currently used, but it's part of the FXML)
    @FXML
    private void handleAddCourse(ActionEvent event) {
        showAlert("Add Course", "You have selected to add a new course.");
    }

    // Action to add course resources and navigate to CourseController using setRoot
    @FXML
    private void handleAddCourseResources(ActionEvent event) throws IOException {
        showAlert("Add Course Resources", "You have selected to add course resources.");
        navigateToCourseController();
    }

    // Action to create quiz (method is not currently used, but it's part of the FXML)
    @FXML
    private void handleCreateQuiz(ActionEvent event) {
        showAlert("Create Quiz", "You have selected to create a quiz.");
    }

    // Action to create assignments (method is not currently used, but it's part of the FXML)
    @FXML
    private void handleCreateAssignments(ActionEvent event) {
        showAlert("Create Assignments", "You have selected to create assignments.");
    }

    // Action to manage student profiles (method is not currently used, but it's part of the FXML)
    @FXML
    private void handleManageStudentProfile(ActionEvent event) {
        showAlert("Manage Student Profiles", "You have selected to manage student profiles.");
    }

    // Action to log out
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        showAlert("Logout", "You have successfully logged out.");
        navigateToUserType();
    }

    // Utility method to display alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Navigate to the user type screen
    private void navigateToUserType() throws IOException {
        App.setRoot("usertype");
    }

    // Navigate to the CourseController using setRoot and pass courses
    private void navigateToCourseController() throws IOException {
        // You can pass the courses to CourseController by setting a static variable or passing it directly
        CourseController.setCourses(App.courses);  // Passing the courses list to CourseController
       // showAlert("CourseController", "You have selected to create course.");

        // Navigate to courseController.fxml
        App.setRoot("courseController");
    }
    
}
