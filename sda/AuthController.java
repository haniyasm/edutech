package sda;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import user.Student;
import database.DatabaseConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;


public class AuthController {
    // Login Fields
   

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    // Signup Fields
    @FXML
    private TextField signupNameField;

    @FXML
    private TextField signupUsernameField;

    @FXML
    private PasswordField signupPasswordField;

    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        String userType = App.getUserType(); // Get the selected user type

        System.out.println(username+" "+password);
    
        boolean loginSuccessful = false;
    
        //Delegate login validation based on user type
        switch (userType) {
            case "Admin":
                loginSuccessful = new database.AdminLogin().validate(username, password);
                break;
            case "Student":
                loginSuccessful = new database.StudentLogin().validate(username, password);
                    // Fetch student details and store in App
                Map<String, Object> studentData = new database.StudentLogin().getStudentInfo(username);
                if (studentData != null) {
                    int id = (int) studentData.get("student_id");
                    String name = (String) studentData.get("name");
                    String uname = (String) studentData.get("username");
                    
                    App.currentStudent = new Student(id, name, uname);
                } else {
                
                    System.err.println("Error: Student data not found for the provided username.");
                }
                //System.out.println(username+" "+password);
                
                break;
            case "Parent":
                loginSuccessful = new database.ParentLogin().validate(username, password);
                //System.out.println(username+" "+password);
                break;
        }
    
        // Handle login result
        if (loginSuccessful) {
            System.out.println(userType + " login successful!\n");
            try {
                // Redirect to the appropriate dashboard
                switch (userType) {
                    case "Admin":
                        App.setRoot("adminMenu");
                        System.out.println(userType);
                        break;
                    case "Student":
                        App.setRoot("studentMenu");
                        System.out.println(userType);
                        break;
                    case "Parent":
                        //App.setRoot("parentDashboard");
                        App.setRoot("parentMenu");
                        System.out.println(userType );
                        break;
                }
            } catch (IOException e) {
                System.err.println("Error redirecting to dashboard: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    @FXML
    private void handleSignUp() {
        String name = signupNameField.getText();
        String username = signupUsernameField.getText();
        String password = signupPasswordField.getText();
        String userType = App.getUserType(); // Get the selected user type
        System.out.println(username+" "+password);
    
        boolean signupSuccessful = false;
    
        // Delegate signup based on user type
        switch (userType) {
            case "Admin":
                signupSuccessful = new database.AdminSignup().registerAdmin(username, password);
                break;
            case "Student":
                signupSuccessful = new database.StudentSignup().registerStudent(name, username, password);
                break;
            case "Parent":
                // For parent, prompt for or predefine the student ID they are linking to
                //int studentId = promptForStudentId(); // Implement this logic as needed
                signupSuccessful = new database.ParentSignup().registerParent(username, password,name);

                break;
        }
    
        // Handle signup result
        if (signupSuccessful) {
            System.out.println(userType + " signup successful!");
            // Redirect to login screen or dashboard
            try {
                App.setRoot("auth");
            } catch (IOException e) {
                System.err.println("Error redirecting after signup: " + e.getMessage());
            }
        } else {
            System.out.println("Signup failed. Please check your details.");
        }
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("usertype");
    }
}

