package sda;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import student.CreateStudyGroups;
import student.StudentPlanController;
import student.enrollClass;
import user.StudentTrackPerformance;

import java.net.URL;





public class studentMenu {
    private static String username;
    private static String password;

    public static void setUsername(String u) {
        username = u;
    }

    public static void setPassword(String u) {
        password = u;
    }
     // Button actions linked from FXML
    @FXML
    private Button enrollCourseButton;
    @FXML
    private Button viewCoursesButton;
    @FXML
    private Button attemptQuizButton;
    @FXML
    private Button attemptAssignmentButton;
  
    @FXML
    private Button logoutButton;

  
    @FXML
    private Button trackPerformanceButton;
    @FXML
    private Button createPersonalPlanButton;
    @FXML
    private Button createStudyGroupButton;
   

    // Action to enroll in a new course
    @FXML
    private void handleEnrollCourse(ActionEvent event) {
        showAlert("Enroll into New Course", "You have selected to enroll in a new course.");
        try {
            enrollClass.setUserName(username);
            enrollClass.setPassword(password);
       
            App.setRoot("enrollClass");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Enroll Class cannot be loaded");
        }
        
    }

    // Action to view enrolled courses
    @FXML
    private void handleViewCourses(ActionEvent event) {
        showAlert("View Enrolled Courses", "You have selected to view your enrolled courses.");
    }

    @FXML
    private void handleViewCourseResources(ActionEvent event) throws IOException {
        // Set username and password for the new controller
        //viewCourseResourcesController.setUserName(username);
        //viewCourseResourcesController.setPassword(password);
        showAlert("View Course Resources", "You have selected to view course resources.");
        App.setRoot("viewCourseResourcesController");
    }

    // Action to attempt a quiz
    @FXML
    private void handleAttemptQuiz(ActionEvent event) {
        showAlert("Attempt Quiz", "You have selected to attempt a quiz.");
        try {
            App.setRoot("/sda/attemptQuiz");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Cannot load attemptAssignment");
        }
    }

    @FXML
    private void handleAttemptExercise(ActionEvent event) throws IOException {
        // Logic to attempt a quiz
        showAlert("Attempt Exercise", "You have selected to attempt a exercise.");
        try {
            App.setRoot("/sda/attemptExercise");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Cannot load attemptAssignment");
        }
    }

    // Action to attempt an assignment
    @FXML
    private void handleAttemptAssignment(ActionEvent event) {
        showAlert("Attempt Assignment", "You have selected to attempt an assignment.");
        try {
            App.setRoot("/sda/attemptAssignment");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Cannot load attemptAssignment");
        }
    }

   

    // Action to track performance
    @FXML
    private void handleTrackPerformance(ActionEvent event) {
        showAlert("Track Performance", "You have selected to track your performance.");
        /*try {
            App.setRoot("/sda/StudentTrackPerformance");
        } catch (IOException e) {
            System.err.println("Error loading Track Performance screen: " + e.getMessage());
            //e.printStackTrace();
        }*/
        try {
            // Load the FXML for Track Performance
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sda/StudentTrackPerformance.fxml"));
            Parent root = loader.load();
        
            // Get the controller for Track Performance and pass the student data
            StudentTrackPerformance controller = loader.getController();
            controller.setStudentData(App.currentStudent); // Assuming App.currentStudent is the logged-in student
        
            // Create a new scene with the loaded root
            Scene scene = new Scene(root);
        
            // Apply the CSS file (if any)
            String css = getClass().getResource("/sda/style/studentTrackPerformance.css").toExternalForm(); // Update with correct path
            scene.getStylesheets().add(css);
        
            // Create a new stage for Track Performance screen
            Stage stage = new Stage();
            stage.setTitle("Track Performance");
            stage.setScene(scene);
            stage.show();
        
        } catch (IOException e) {
            System.err.println("Error loading Track Performance screen: " + e.getMessage());
        }
        /*try {
            StudentTrackPerformance.setStudentData(App.currentStudent);
            App.setRoot("StudentTrackPerformance");
        } catch (Exception e) {
            // TODO: handle exception
            showAlert("Track Student", "Cannot load Tack Student");
        }*/
        

        
       

    }
    

    // Action to create a personal plan
    @FXML
    private void handleCreatePersonalPlan(ActionEvent event) {
        //showAlert("Create Personal Plan", "You have selected to create a personal plan.");
        try {
            StudentPlanController.setStudentID(App.currentStudent.getId());
            App.setRoot("studentplan");
        } catch (Exception e) {
            
            showAlert("Create Student Plan", "Cannot load Create Plan");

        }
       
    }

    // Action to create a study group
    @FXML
    private void handleCreateStudyGroup(ActionEvent event) {
        //showAlert("Create Study Group", "You have selected to create a study group.");

        try {
            CreateStudyGroups.setStudentID(App.currentStudent.getId());
            App.setRoot("studyGroups");
        } catch (Exception e) {
            
            showAlert("Create Student Plan", "Cannot load Create Plan");

        }
    }

    ///summaryNotesController
   

    // Action to logout and return to user type screen
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            App.setRoot("usertype");
        } catch (IOException e) {
            showAlert("Error", "Failed to load the user type screen.");
        }
    }

    // Utility method to display alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
