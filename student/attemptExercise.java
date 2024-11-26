package student;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import sda.App;
import assessments.Exercise;
import assessments.Question;
import courses.Course;


import java.util.ArrayList;
import java.util.List;

public class attemptExercise {
    @FXML
    private ListView<String> courseListView;

    @FXML
    private VBox exerciseContainer;

    @FXML
    public void initialize() {
        // Populate courses in ListView
        List<Course> courses = App.currentStudent.getCourses();
        for (Course course : courses) {
            courseListView.getItems().add(course.getCourseName());
        }

        // Add listener for course selection
        courseListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayExercises(newValue);
            }
        });
    }

    private void displayExercises(String course) {
        // Clear previous exercises
        exerciseContainer.getChildren().clear();

        // Get exercises for the selected course
        List<Course> courses = App.currentStudent.getCourses();

        List<Exercise> exercises = new ArrayList<>();
        for (Course c : courses) {
            if (c.getCourseName().equalsIgnoreCase(course)) {
                exercises = c.getExercises();
                break;
            }
        }

        // Add each exercise with a "Show Answers" button
        for (Exercise exercise : exercises) {
            VBox exerciseBox = new VBox(10); // Container for the exercise
            Label exerciseLabel = new Label("Exercise: " + exercise.getAssessmentName());

            exerciseBox.getChildren().add(exerciseLabel); // Add the exercise name label

            // Create a VBox for questions
            VBox questionsBox = new VBox(5);
            for (Question question : exercise.getQuestions()) {
                VBox questionBox = new VBox(5); // Container for each question
                Label questionLabel = new Label("Question: " + question.getQuestion());

                // Add the question label to the questionBox
                questionBox.getChildren().add(questionLabel);

                // Add the questionBox to the questionsBox
                questionsBox.getChildren().add(questionBox);
            }

            // Add the "Show Answers" button
            Button showAnswersButton = new Button("Show Answers");
            showAnswersButton.setOnAction(event -> showAnswers(questionsBox, exercise.getQuestions()));

            // Add the questionsBox and the button to the exerciseBox
            exerciseBox.getChildren().addAll(questionsBox, showAnswersButton);

            // Add the exerciseBox to the exerciseContainer
            exerciseContainer.getChildren().add(exerciseBox);
        }
    }

    private void showAnswers(VBox questionsBox, List<Question> questions) {
        int index = 0;
        for (var node : questionsBox.getChildren()) {
            if (node instanceof VBox questionBox && index < questions.size()) {
                // Add the answer label if not already present
                if (questionBox.getChildren().size() < 2) {
                    Label answerLabel = new Label("Answer: " + questions.get(index).getAnswer());
                    questionBox.getChildren().add(answerLabel);
                }
                index++;
            }
        }
    }
    
}
