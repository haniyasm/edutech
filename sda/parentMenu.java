package sda;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class parentMenu {

    // Action to view student performance
    @FXML
    private void handleViewStudentPerformance(ActionEvent event) {
        // Logic for viewing student performance
        //showAlert("View Student Performance", "You have selected to view student performance.");
        try {
            App.setRoot("/sda/ParentTrackPerformance");
        } catch (IOException e) {
            System.err.println("Error loading Track Performance screen: " + e.getMessage());
            //e.printStackTrace();
        }
        
    }

    // Action to log out
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        // Logic for logout
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
    private void navigateToUserType() throws IOException {

    	App.setRoot("usertype");
        
    }
    
}
