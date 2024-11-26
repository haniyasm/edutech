package student;

import sda.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class viewCourseResourcesController {
    @FXML
    private ListView<String> coursesListView;

    @FXML
    private Button proceedButton;

    private ObservableList<String> enrolledCourses = FXCollections.observableArrayList(
            "Mathematics",
            "Science",
            "History",
            "Geography",
            "English"
    );

    private String selectedCourse;

    @FXML
    public void initialize() {
        coursesListView.setItems(enrolledCourses);

        coursesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCourse = newValue;
        });
    }

    @FXML
    public void handleProceedButton(ActionEvent event) {
        if (selectedCourse == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Course Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to proceed.");
            alert.showAndWait();
        } 
        else {
            System.out.println("Proceeding with course: " + selectedCourse);
            try {
            	typeOfResources.setSelectedCourse(selectedCourse);
                App.setRoot("typeOfResources");
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
}
