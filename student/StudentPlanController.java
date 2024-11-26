package student;

import java.util.List;

import database.UpdateStudentPlan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import sda.App;
public class StudentPlanController {

    private static int studentID;

    @FXML
    private VBox plansContainer;
    @FXML
    private Label someLabel;

    @FXML
    private VBox formContainer;
    
    @FXML
    private TextField idField, nameField, timeAvailableField, subjectsField, chaptersField, priorityField;
    

    // Setter method for studentID passed from the main app
    public static void setStudentID(int studentID) {
        StudentPlanController.studentID = studentID;
    }

    public void initialize() {
        // This method is called after the FXML is loaded.
        System.out.println("StudentPlanController initialized!");
    }

    private void clearForm() {
        nameField.clear();
        timeAvailableField.clear();
        subjectsField.clear();
        chaptersField.clear();
        priorityField.clear();
    }

    
    public void handleAddPlan() {
        System.out.println("Add Plan clicked!");
    
        // Hide the plans container and show the form container
        plansContainer.setVisible(false);
        plansContainer.setManaged(false);
    
        formContainer.setVisible(true);
        formContainer.setManaged(true);
    
        clearForm(); // Clear any previous form data
    }
    


    public void handleSavePlan() {
        // Retrieve the values from the form fields
        formContainer.setVisible(false);
        formContainer.setManaged(false);
        String name = nameField.getText();
        int timeAvailable = Integer.parseInt(timeAvailableField.getText());
        int subjects = Integer.parseInt(subjectsField.getText());
        int chapters = Integer.parseInt(chaptersField.getText());
        String priority = priorityField.getText();
        
        // Assuming you have the studentID available (e.g., from a logged-in user session or another method)
        int studentID = 1; // Change this as needed, perhaps from a logged-in session

        // Call the addStudentPlan method to insert the new plan into the database
        boolean success = UpdateStudentPlan.addStudentPlan(studentID, name, timeAvailable, subjects, chapters, priority);

        if (success) {
            System.out.println("Plan successfully saved!");
            // Optionally, clear the fields or close the form after saving
            clearForm();
        } else {
            System.out.println("Failed to save plan.");
        }

        // Hide the form after saving
        formContainer.setVisible(false);
    }

    //Method to load student plans from the database
    private void loadStudentPlans() {
        // Clear previous content in the plans container
        plansContainer.getChildren().clear();
    
        // Fetch plans from the database
        List<StudentPlan> plans = UpdateStudentPlan.getStudentPlansByStudentID(studentID);
    
        if (plans.isEmpty()) {
            Label noPlansMessage = new Label("No plans created yet.");
            noPlansMessage.setStyle("-fx-font-size: 16px; -fx-text-fill: #888888;");
            plansContainer.getChildren().add(noPlansMessage);
        } else {
            int count=1;
            for (StudentPlan plan : plans) {
                // Create a VBox for each plan (styled as a card)
                VBox planCard = new VBox();
                planCard.getStyleClass().add("plan-card");
                planCard.setSpacing(10);
    
                // Add plan details
                Label nameLabel = new Label("Index#: " + count);
                Label timeLabel = new Label("Time Available: " + plan.getTimeAvailable() + " hrs");
                Label subjectsLabel = new Label("Subjects: " + plan.getSubjects());
                Label chaptersLabel = new Label("Chapters: " + plan.getChapters());
                Label priorityLabel = new Label("Priority: " + plan.getPriority());
    
                // Add details to the card
                planCard.getChildren().addAll(nameLabel, timeLabel, subjectsLabel, chaptersLabel, priorityLabel);
    
                // Add the card to the plans container
                plansContainer.getChildren().add(planCard);
                count++;
            }
        }
    }


    @FXML
private void handleViewPlans() {
    System.out.println("View Plans clicked!");
    formContainer.setVisible(false);
    formContainer.setManaged(false);

    // Clear the container before adding new plan cards
    plansContainer.getChildren().clear();

    // Fetch plans for the logged-in student
    List<StudentPlan> plans = UpdateStudentPlan.getStudentPlansByStudentID(studentID);

    // Add plan cards to the container
    if (plans.isEmpty()) {
        Label noPlansLabel = new Label("No plans created yet.");
        noPlansLabel.setStyle("-fx-font-size: 16; -fx-text-fill: red;");
        plansContainer.getChildren().add(noPlansLabel);
    } else {
        int c=1;
        for (StudentPlan plan : plans) {
            VBox planCard = createPlanCard(plan,c);
            c=c+1;
            plansContainer.getChildren().add(planCard);
        }
    }
    plansContainer.setStyle("-fx-border-color: blue; -fx-background-color: lightgrey;");
    plansContainer.setVisible(true);
    plansContainer.setManaged(true);
}

// Helper method to create a styled card for a plan
private VBox createPlanCard(StudentPlan plan, int count) {
    VBox planCard = new VBox();
    planCard.setSpacing(10);
    planCard.setAlignment(Pos.CENTER_LEFT);
    planCard.setStyle("-fx-border-color: black; -fx-background-color: #f0f0f0; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");

    Label nameLabel = new Label("Index#: " + count);
    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    Label detailsLabel = new Label(
        "Time Available: " + plan.getTimeAvailable() + " hrs\n" +
        "Subjects: " + plan.getSubjects() + "\n" +
        "Chapters: " + plan.getChapters() + "\n" +
        "Priority: " + plan.getPriority()
    );
    detailsLabel.setStyle("-fx-font-size: 14px;");

    planCard.getChildren().addAll(nameLabel, detailsLabel);
    return planCard;
}


    @FXML
private void handleBack(ActionEvent event) {
    try {
        // Assuming you have a method to load the student menu screen
        App.setRoot("studentMenu");
    } catch (Exception e) {
        System.out.println("Cannot return to the student menu.");
    }
}


    
}
