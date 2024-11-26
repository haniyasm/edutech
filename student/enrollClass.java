package student;

import sda.App;

import java.util.*;

import courses.Course;
import user.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class enrollClass {

    private static String username;
    private static String password;

    @FXML
    private ListView<String> courseListView;
    @FXML
    private Button enrollButton;
    @FXML
    private Button backButton; // Back button reference

    private ObservableList<String> courseNames;

    public static void setUserName(String u) {
        username = u;
    }

    public static void setPassword(String p) {
        password = p;
    }

    @FXML
    private void initialize() {
        courseNames = FXCollections.observableArrayList();

        // Populate course names from Main.courses
        for (Course course : App.courses) {
            courseNames.add(course.getCourseName());
        }

        // Set the course list to ListView
        courseListView.setItems(courseNames);
    }

    @FXML
    private void handleEnrollButton(ActionEvent event) {
        String selectedCourseName = courseListView.getSelectionModel().getSelectedItem();

        if (selectedCourseName == null) {
            showAlert("No Course Selected", "Please select a course to enroll in.");
            return;
        }

        // Find the specific student using username and password
        Student specificStudent = findStudentByUsernameAndPassword(username, password);

        if (specificStudent == null) {
            showAlert("Student Not Found", "No student matches the provided credentials.");
            return;
        }

        // Find the selected course from Main.courses
        Course selectedCourse = App.courses.stream()
                .filter(course -> course.getCourseName().equals(selectedCourseName))
                .findFirst()
                .orElse(null);

        if (selectedCourse != null) {
            // Add the course to the specific student's course list
            specificStudent.getCourses().add(selectedCourse);
            showAlert("Enrollment Successful", "You have been enrolled in " + selectedCourseName);
        } else {
            showAlert("Course Not Found", "The selected course could not be found.");
        }
    }

    @FXML
    private void handleBackButton(ActionEvent event) {
        try {
            App.setRoot("studentMenu");
        } 
        catch (Exception e) {
            showAlert("Navigation Error", "Could not navigate to the previous menu.");
        }
    }

    private Student findStudentByUsernameAndPassword(String username, String password) {
        System.out.println("App.currentstudent.username  "+App.currentStudent.getUsername());
        System.out.println("App.currentstudent.password  "+App.currentStudent.getPassword());

         boolean loginSuccessful = new database.StudentLogin().validate(App.currentStudent.getUsername(), App.currentStudent.getPassword());
                    // Fetch student details and store in App
                Map<String, Object> studentData = new database.StudentLogin().getStudentInfo(username);
                    if (studentData==null)
                    {
                        System.out.println("Student Data in enroll Class ftn is null");
                    }

                    int id = (int) studentData.get("student_id");
                    String name = (String) studentData.get("name");
                    String uname = (String) studentData.get("username");
                    Student n=new Student(id,name,uname);
                    return n;
                
        //return App.getStudents().stream().filter(user -> user instanceof Student).map(user -> (Student) user).filter(st -> st.getUsername().equals(username) && st.getPassword().equals(password)).findFirst().orElse(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
