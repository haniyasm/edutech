package student;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sda.App;
import assessments.Assignments;
import assessments.Question;
import assessments.Quiz;
import courses.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class attemptAssignment {
    @FXML
    private ListView<String> courseListView;

    @FXML
    private ListView<String> assignmentListView;

    @FXML
    private VBox questionContainer;

    @FXML
    private Button goBackButton;

    private List<Assignments> currentAssignments = new ArrayList<>();
    private Course selectedCourse;
    private Assignments selectedAssignment;

    private Label timerLabel;
    private int remainingSeconds; // Countdown time in seconds

    @FXML
    public void initialize() {
        // Populate courses in ListView
        List<Course> courses = App.currentStudent.getCourses();
        for (Course course : courses) {
            courseListView.getItems().add(course.getCourseName());
        }

        // Listener for course selection
        courseListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
            	
            	// Find and store the selected course
                for (Course course : courses) {
                    if (course.getCourseName().equalsIgnoreCase(newValue)) {
                        selectedCourse = course; // Store the selected course
                        break;
                    }
                }
            	
            	displayAssignments(newValue);
            	
            }
        });

        // Listener for quiz selection
        assignmentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayQuestions(newValue);
            }
        });
    }

    private void displayAssignments(String course) {
        // Clear previous quizzes
    	assignmentListView.getItems().clear();
        currentAssignments.clear();
        questionContainer.getChildren().clear();

        // Get quizzes for the selected course
        List<Course> courses = App.currentStudent.getCourses();
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(course)) {
            	currentAssignments = c.getAssignments(); // Retrieve quizzes (exercises) for the selected course
                break;
            }
        }

        // Add quiz names to the quiz ListView
        for (Assignments assignment : currentAssignments) {
        	assignmentListView.getItems().add(assignment.getAssessmentName());
        }
    }

    private void displayQuestions(String assignmentName) {
        // Clear previous questions
        questionContainer.getChildren().clear();

        // Find the selected quiz
        selectedAssignment = null;
        for (Assignments assignment : currentAssignments) {
            if (assignment.getAssessmentName().equalsIgnoreCase(assignmentName)) {
            	selectedAssignment = assignment;
                break;
            }
        }

        if (selectedAssignment != null) {
            // Add questions to the question container
            for (Question question : selectedAssignment.getQuestions()) {
                VBox questionBox = new VBox(5);
                Label questionLabel = new Label("Question: " + question.getQuestion());
                Label marksLabel = new Label("Marks: (" + question.getMarks() + ")");
                TextField answerField = new TextField();
                answerField.setPromptText("Enter your answer");

                questionBox.getChildren().addAll(questionLabel, marksLabel, answerField);
                questionContainer.getChildren().add(questionBox);
            }

            // Add a submit button
            Button submitButton = new Button("Submit Assignment");
            submitButton.setOnAction(event -> handleSubmit(selectedAssignment));
            questionContainer.getChildren().add(submitButton);

            // Add a timer label
            timerLabel = new Label("Deadline: " + selectedAssignment.getDeadline());
            questionContainer.getChildren().add(0, timerLabel); // Add timer at the top of the question container

        }
    }


    private void handleSubmit(Assignments assignment) {
    	
    	if (assignment.getDeadline().isBefore(java.time.LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Deadline Passed");
            alert.setHeaderText("Assignment Deadline Missed");
            alert.setContentText("The deadline for this assignment has passed. You cannot submit it.");
            alert.showAndWait();
            
            handleGoBack(); // Navigate back to the previous screen
            return; // Exit the method
        }
    	
    	float marksObtained = 0.0f;
        int questionIndex = 0;

        // Iterate through children of questionContainer, filtering for VBoxes
        for (var node : questionContainer.getChildren()) {
            if (node instanceof VBox questionBox) { // Use pattern matching for type safety
                // Retrieve the TextField for the answer
                if (questionIndex < assignment.getQuestions().size()) {
                    Question question = assignment.getQuestions().get(questionIndex);

                    // Get the answer field (3rd child in the VBox)
                    TextField answerField = (TextField) questionBox.getChildren().get(2);

                    // Compare the user's answer with the correct answer (ignoring case)
                    String userAnswer = answerField.getText().trim();
                    String correctAnswer = question.getAnswer().trim();
                    if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        marksObtained += question.getMarks(); // Add marks for correct answers
                    }

                    questionIndex++; // Move to the next question
                }
            }
        }
        
        App.currentStudent.attemptedAssignment(selectedCourse, selectedAssignment, marksObtained);
        App.currentStudent.printAssignmentMarks();
    	

        // Logic to handle quiz submission
        System.out.println("Assignment submitted: " + assignment.getAssessmentName());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Assignment Submission");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully submitted the Assignment: " + assignment.getAssessmentName());
        alert.showAndWait();
        handleGoBack();
        
    }

    

    @FXML
    private void handleGoBack() {
        try {
            App.setRoot("studentMenu");
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
