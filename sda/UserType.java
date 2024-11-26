package sda;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class UserType {
    @FXML
    private Button adminButton, studentButton, parentButton;

    // Called when the Admin button is clicked
    @FXML
    private void onAdminSelected() {
        navigateToAuth("Admin");
    }

    // Called when the Student button is clicked
    @FXML
    private void onStudentSelected() {
        navigateToAuth("Student");
       
    }

    // Called when the Parent button is clicked
    @FXML
    private void onParentSelected() {
        navigateToAuth("Parent");
    }

    // Navigate to the auth screen
    private void navigateToAuth(String userType) {
        try {
            // Store user type in a global state if needed (optional)
            App.setUserType(userType);

            // Load the auth.fxml for login/signup
            App.setRoot("auth");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
