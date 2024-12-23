package assessments;

import java.io.IOException;
import java.util.ArrayList;


import sda.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import user.Admin;
 

public class addExercise {
    @FXML private TextField courseNameField;
    @FXML private TextField topicNameField;
    @FXML private TextField questionField;
    @FXML private TextField exerciseNameField;
    @FXML private Button uploadExerciseButton;
    @FXML private TableView<Question> questionsTable;
    @FXML private TableColumn<Question, String> topicColumn;
    @FXML private TableColumn<Question, String> questionColumn;
    @FXML private TableColumn<Question, String> answerColumn;
    @FXML private TableColumn<Question, String> marksColumn;

    private final ObservableList<Question> questionList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Link table columns with the Question properties
        topicColumn.setCellValueFactory(new PropertyValueFactory<>("topic"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        answerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        marksColumn.setCellValueFactory(new PropertyValueFactory<>("marks"));

        // Set data for the TableView
        questionsTable.setItems(questionList);
    }

    @FXML
    private void handleAddQuestion() {
        String topic = topicNameField.getText();
        String question = questionField.getText();

        if (topic.isEmpty() || question.isEmpty() ) {
            showAlert("Validation Error", "Please fill all fields before adding a question.", Alert.AlertType.WARNING);
            return;
        }

        // Add a new question to the list
        questionList.add(new Question(topic, question, "", 0));

        // Clear input fields
        topicNameField.clear();
        questionField.clear();
    }

    @FXML
    private void handleUploadExercise() {
   
        
        String exerciseName = exerciseNameField.getText();
        String courseName = courseNameField.getText();

        if (exerciseName.isEmpty() ||  courseName.isEmpty()) {
            showAlert("Validation Error", "Please fill all Exercise details before uploading.", Alert.AlertType.WARNING);
            return;
        }

        if (questionList.isEmpty()) {
            showAlert("Validation Error", "Please add at least one question before uploading the Exercise.", Alert.AlertType.WARNING);
            return;
        }

        
        try {
        	ArrayList<Question> qslist = new ArrayList<>();
        	qslist.addAll(questionList);
        	boolean check = Admin.getInstance().addExercise(exerciseName,courseName, qslist);
			if(check) 
			{
				// Display confirmation alert
		        showAlert("Success", "Exercise uploaded successfully for course: " + courseName, Alert.AlertType.INFORMATION);
				App.setRoot("addResources"); 
			}
			else
			{
				showAlert("Course Error", "Please add correct course name.", Alert.AlertType.WARNING);
				courseNameField.clear();
			}
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleGoBack() {
        try {
            App.setRoot("addResources");
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Navigation Error");
            alert.setContentText("Could not go back to the previous screen.");
            alert.showAndWait();
        }
    }
    
}
