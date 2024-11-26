package student;

import java.util.List;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import sda.App;
import user.Student;

public class CreateStudyGroups {
    private static int studentID;
    
    @FXML
    private VBox groupCreationSection; // The section to show/hide

    @FXML
private VBox groupsContainer; // Container for dynamically created group cards

    @FXML
    private TableView<StudentRow> studentsTable;
    @FXML
    private TableColumn<StudentRow, Integer> colStudentID;
    @FXML
    private TableColumn<StudentRow, String> colStudentName;
    
    @FXML
    private TableColumn<StudentRow, String> colStudentUsername;
    @FXML
    private TableColumn<StudentRow, Button> colAction;

    @FXML
    private TextArea logTextArea;

    private ObservableList<StudentRow> studentRows = FXCollections.observableArrayList();
    private ArrayList<Integer> selectedStudentIds = new ArrayList<>();  // Store selected student IDs

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
        CreateStudyGroups.studentID = studentID;
    }

    public void initialize() {
        // This method is called after the FXML is loaded.
        System.out.println("StudentPlanController initialized!");
        
        // Set up table columns
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("id")); // Property reference using property method
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colStudentUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Set up the custom cell factory for the Action button column
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        colAction.setCellFactory(new Callback<TableColumn<StudentRow, Button>, javafx.scene.control.TableCell<StudentRow, Button>>() {
            @Override
            public javafx.scene.control.TableCell<StudentRow, Button> call(TableColumn<StudentRow, Button> param) {
                return new javafx.scene.control.TableCell<StudentRow, Button>() {
                    @Override
                    protected void updateItem(Button item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(item);
                        }
                    }
                };
            }
        });
    }

    private void addStudentToGroup(Student student) {
        // Add student to the group (logically, and update the log)
        selectedStudentIds.add(student.getId());  // Add the student to the temporary list
        logTextArea.appendText("Added: " + student.getUsername() + "\n");
        System.out.println("Added student to group: " + student.getUsername());
    }

    private void loadStudents() {
        // Fetch all students except the logged-in user
        UserService userService = new UserService();
        userService.setLoggedInStudentId(studentID);
        List<Student> students = userService.getLoggedInStudents(); // Fetch all students
        students.removeIf(student -> student.getId() == studentID); // Remove logged-in student

        // Populate the table
        studentRows.clear(); // Clear previous data if any
        for (Student student : students) {
            Button addButton = new Button("Add");
            addButton.setOnAction(event -> addStudentToGroup(student));  // When button clicked, add student to list
            studentRows.add(new StudentRow(student.getId(), student.getUsername(), addButton));
        }

        studentsTable.setItems(studentRows);
    }

            private VBox createGroupCard(StudyGroup group) {
                // Outer container for the group card
                VBox groupCard = new VBox();
                groupCard.setSpacing(5);
                groupCard.setAlignment(Pos.CENTER);
                groupCard.setPrefWidth(300);
                groupCard.setPadding(new Insets(10));
                groupCard.setStyle("-fx-background-color: #e3f2fd; -fx-background-radius: 10; -fx-border-color: #64b5f6; -fx-border-radius: 10;");

                // Circle for group icon
                Circle groupCircle = new Circle(30, Color.LIGHTBLUE);
                groupCircle.setStroke(Color.DARKBLUE);
                groupCircle.setStrokeWidth(2);

                // Label for group name
                Label groupNameLabel = new Label(group.getGroupName());
                groupNameLabel.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: #1e88e5;");

                // Label for group ID
                Label groupIdLabel = new Label("Group ID: " + group.getId());
                groupIdLabel.setStyle("-fx-font-size: 14; -fx-text-fill: #616161;");

                // Add elements to the group card
                groupCard.getChildren().addAll(groupCircle, groupNameLabel, groupIdLabel);
                groupCard.setOnMouseClicked(event -> {
                    System.out.println("Clicked on group: " + group.getGroupName());
                    // Add additional actions here
                });
                return groupCard;
            }

    @FXML
    private void handleCreateStudyGroup() {
        // This method is called after the FXML is loaded.
        System.out.println("Add Groups clicked!");
        groupsContainer.setVisible(false);
        groupsContainer.setManaged(false);
        
        // Show the group creation section
        groupCreationSection.setVisible(true);
        groupCreationSection.setManaged(true);

        // Load students into the table
        loadStudents();
    }

    @FXML
    private void handleSaveGroups() {
        System.out.println("You clicked Save Button");

        // Create the study group (it will return a group ID)
        int groupId = UserService.addStudyGroup();
        UserService.addStudentToGroup(studentID, groupId);

        // Add the selected students to the newly created group
        for (Integer stdId : selectedStudentIds) {
            UserService.addStudentToGroup(stdId, groupId);
        }

        // Clear the selected students and log after saving
        selectedStudentIds.clear();
        logTextArea.appendText("Study Group created and students added.\n");
    }

    

                @FXML
            private void handleViewStudyGroups() {
                System.out.println("View Study Groups clicked!");
                groupCreationSection.setVisible(false);
                groupCreationSection.setManaged(false);

                // Clear the container before adding new group cards
                groupsContainer.getChildren().clear();


                // Fetch groups for the logged-in student
                UserService userService = new UserService();
                userService.setLoggedInStudentId(studentID); // Ensure the correct ID is set
                List<StudyGroup> groups = userService.getStudyGroupsForStudent(studentID);

                // Add group cards to the container
                if (groups.isEmpty()) {
                    Label noGroupsLabel = new Label("You are not part of any study groups.");
                    noGroupsLabel.setStyle("-fx-font-size: 16; -fx-text-fill: red;");
                    groupsContainer.getChildren().add(noGroupsLabel);
                } else {
                    for (StudyGroup group : groups) {
                        VBox groupCard = createGroupCard(group);
                        groupsContainer.getChildren().add(groupCard);
                        groupsContainer.setStyle("-fx-border-color: blue; -fx-background-color: lightgrey;");
                        groupsContainer.setVisible(true);
                        groupsContainer.setManaged(true);
                    }
                }
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
