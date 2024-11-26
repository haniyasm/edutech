package student;

import javafx.animation.KeyFrame;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sda.App;
import assessments.Question;
import assessments.Quiz;
import courses.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class attemptQuiz {
    @FXML
    private ListView<String> courseListView;

    @FXML
    private ListView<String> quizListView;

    @FXML
    private VBox questionContainer;

    @FXML
    private Button goBackButton;

    private List<Quiz> currentQuizzes = new ArrayList<>();
    private Course selectedCourse;
    private Quiz selectedQuiz;

    private Timeline timer;
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
            	
                displayQuizzes(newValue);
            }
        });

        // Listener for quiz selection
        quizListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayQuestions(newValue);
            }
        });
    }

    private void displayQuizzes(String course) {
        // Clear previous quizzes
        quizListView.getItems().clear();
        currentQuizzes.clear();
        questionContainer.getChildren().clear();

        // Get quizzes for the selected course
        List<Course> courses = App.currentStudent.getCourses();
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(course)) {
                currentQuizzes = c.getQuizzes(); // Retrieve quizzes (exercises) for the selected course
                break;
            }
        }

        // Add quiz names to the quiz ListView
        for (Quiz quiz : currentQuizzes) {
            quizListView.getItems().add(quiz.getAssessmentName());
        }
    }

    private void displayQuestions(String quizName) {
        // Clear previous questions
        questionContainer.getChildren().clear();

        // Stop the existing timer if it's running
        if (timer != null) {
            timer.stop();
        }

        // Find the selected quiz
        selectedQuiz = null;
        for (Quiz quiz : currentQuizzes) {
            if (quiz.getAssessmentName().equalsIgnoreCase(quizName)) {
                selectedQuiz = quiz;
                break;
            }
        }

        if (selectedQuiz != null) {
            // Add questions to the question container
            for (Question question : selectedQuiz.getQuestions()) {
                VBox questionBox = new VBox(5);
                Label questionLabel = new Label("Question: " + question.getQuestion());
                Label marksLabel = new Label("Marks: (" + question.getMarks() + ")");
                TextField answerField = new TextField();
                answerField.setPromptText("Enter your answer");

                questionBox.getChildren().addAll(questionLabel, marksLabel, answerField);
                questionContainer.getChildren().add(questionBox);
            }

            // Add a submit button
            Button submitButton = new Button("Submit Quiz");
            submitButton.setOnAction(event -> handleSubmit(selectedQuiz));
            questionContainer.getChildren().add(submitButton);

            // Add a timer label
            timerLabel = new Label();
            questionContainer.getChildren().add(0, timerLabel); // Add timer at the top of the question container

            // Start the timer (e.g., 2 minutes = 120 seconds)
            startTimer(selectedQuiz.getDuration());
        }
    }

    private void startTimer(int durationInMinutes) {
        remainingSeconds = durationInMinutes * 60;

        // Update the timer label initially
        updateTimerLabel();

        // Create a timeline to decrement the timer every second
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingSeconds--;
            updateTimerLabel();

            // Check if the time has run out
            if (remainingSeconds <= 0) {
                timer.stop();
                handleTimeOut();
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        timer.play(); // Start the timer
    }

    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
    }

    private void handleTimeOut() {
        // Schedule the alert to display after the current animation frame
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Time Out");
            alert.setHeaderText("Quiz Time Over");
            alert.setContentText("Your time for this quiz has expired!");
            alert.showAndWait();

            // Disable further actions for the quiz
            questionContainer.getChildren().clear();
            timerLabel.setText("Time Over");
            
            try {
                App.setRoot("studentMenu");
            } catch (IOException e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Navigation Error");
                alert.setContentText("Could not go back to the previous screen.");
                alert.showAndWait();
            }
            
        });
    }


    private void handleSubmit(Quiz quiz) {
        // Stop the timer when submitting the quiz
        if (timer != null) {
            timer.stop();
        }

        float marksObtained = 0.0f;
        int questionIndex = 0;

        // Iterate through children of questionContainer, filtering for VBoxes
        for (var node : questionContainer.getChildren()) {
            if (node instanceof VBox questionBox) { // Use pattern matching for type safety
                // Retrieve the TextField for the answer
                if (questionIndex < quiz.getQuestions().size()) {
                    Question question = quiz.getQuestions().get(questionIndex);

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

        // Update the quiz marks for the current student
        App.currentStudent.attemptedQuiz(selectedCourse, selectedQuiz, marksObtained);
        App.currentStudent.printQuizMarks();

        // Display submission success
        System.out.println("Quiz submitted: " + quiz.getAssessmentName());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Submission");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully submitted the quiz: " + quiz.getAssessmentName() +
                             "\nMarks Obtained: " + marksObtained);
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
