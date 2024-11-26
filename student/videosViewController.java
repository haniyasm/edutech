package student;

import courses.Course;
import database.Main;
import sda.App;
import courseResources.Videos;
import courseResources.Resources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class videosViewController {
    
    @FXML
    private Label selectedCourseLabel;

    @FXML
    private ComboBox<Videos> videosComboBox;

    @FXML
    private Label videoUrlLabel;

    @FXML
    private WebView webView;

    @FXML
    private VBox rootVBox;

    // Variable to store the selected course
    private static String selectedCourse;

    @FXML
    public void initialize1() {
        // Set padding programmatically
        rootVBox.setPadding(new Insets(10, 10, 10, 10));
    }

    // Method to set the selected course (called from the previous screen)
    public static void setSelectedCourse(String name) {
        selectedCourse = name;
    }

    @FXML
    public void initialize() {
        // Display the selected course
        if (selectedCourse != null) {
            selectedCourseLabel.setText("Selected Course: " + selectedCourse);

            // Load the videos for the selected course
            loadVideosForCourse();
        }
    }

    private void loadVideosForCourse() {
        if (selectedCourse != null) {
            // Fetch the selected course from the Main course list
            Course course = findCourseByName(selectedCourse);
            if (course != null) {
                ObservableList<Videos> videos = FXCollections.observableArrayList();
                for (Resources resource : course.getResources()) {
                    if (resource instanceof Videos) {
                        videos.add((Videos) resource);
                    }
                }
                videosComboBox.setItems(videos);
            }
        }
    }

    private Course findCourseByName(String courseName) {
        // Iterate through the courses list in Main and find the course by name
        for (Course course : App.courses) {
            if (course.getCourseName().equalsIgnoreCase(courseName)) {
                return course;
            }
        }
        return null; // Return null if course not found
    }

    @FXML
    public void handleVideoSelection(ActionEvent event) {
        Videos selectedVideo = videosComboBox.getValue();
        if (selectedVideo != null) {
            // Display the URL for the selected video
            String videoUrl = selectedVideo.getVideoUrl();
            videoUrlLabel.setText("Video URL: " + videoUrl);

            // Load the video in the WebView
            webView.getEngine().load(videoUrl);
        }
    }

    @FXML
    public void handlePlayButton(ActionEvent event) {
        Videos selectedVideo = videosComboBox.getValue();
        if (selectedVideo != null) {
            String videoUrl = selectedVideo.getVideoUrl();
            
            // Validate the URL
            if (videoUrl == null || videoUrl.trim().isEmpty()) {
                showError("Error", "Video URL not found.");
                return;
            }
            
            // Play the video using YouTube URL
            if (videoUrl.startsWith("https://www.youtube.com/watch?v=")) {
                // Directly load the YouTube URL in WebView to play
                webView.getEngine().load(videoUrl);
            } else {
                showError("Invalid URL", "The URL is not a valid YouTube URL.");
            }
        } else {
            showError("No Video Selected", "Please select a video to play.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
