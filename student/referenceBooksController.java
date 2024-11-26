package student;

import courses.Course;
import sda.App;
import courseResources.Book;
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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.concurrent.Task;


public class referenceBooksController {
    	//https://ocw.mit.edu/ans7870/resources/Strang/Edited/Calculus/Calculus.pdf
        @FXML
    private Label selectedCourseLabel;

    @FXML
    private ComboBox<Book> referenceBooksComboBox;

    @FXML
    private Label bookUrlLabel;

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

            // Load the books for the selected course
            loadBooksForCourse();
        }
    }

    private void loadBooksForCourse() {
        if (selectedCourse != null) {
            // Fetch the selected course from the Main course list
            Course course = findCourseByName(selectedCourse);
            if (course != null) {
                ObservableList<Book> books = FXCollections.observableArrayList();
                for (Resources resource : course.getResources()) {
                    if (resource instanceof Book) {
                        books.add((Book) resource);
                    }
                }
                referenceBooksComboBox.setItems(books);
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
    public void handleBookSelection(ActionEvent event) {
        Book selectedBook = referenceBooksComboBox.getValue();
        if (selectedBook != null) {
            // Display the URL for the selected book
            String bookUrl = selectedBook.getBookUrl();
            bookUrlLabel.setText("Download URL: " + bookUrl);
            webView.getEngine().load(bookUrl); // Load the book URL into WebView
        }
    }

    @FXML
    public void handleDownloadButton(ActionEvent event) {
        Book selectedBook = referenceBooksComboBox.getValue();
        if (selectedBook != null) {
            String bookUrl = selectedBook.getBookUrl();
            
            // Validate the URL
            if (bookUrl == null || bookUrl.trim().isEmpty()) {
                showError("Error", "Book URL not found.");
                return;
            }
            
            if (!bookUrl.startsWith("http://") && !bookUrl.startsWith("https://")) {
                showError("Invalid URL", "The book URL is not valid. Please check the URL.");
                return;
            }

            // Start downloading the book
            try {
                String sanitizedFileName = selectedBook.getTitle().replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";
                downloadFile(bookUrl, sanitizedFileName);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                showError("Download Failed", "An error occurred while downloading the book.");
            }
        } else {
            showError("No Book Selected", "Please select a book to download.");
        }
    }

    private void downloadFile(String fileUrl, String destinationFileName) throws IOException, URISyntaxException {
        // Open connection to the file URL using URI and URL
        URL url = new URI(fileUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Initialize progress
        Task<Void> downloadTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try (InputStream inputStream = connection.getInputStream()) {
                    String destinationPath = "downloads/" + destinationFileName;
                    Files.createDirectories(Paths.get("downloads"));
                    Files.copy(inputStream, Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                } 
                catch (IOException e) {
                    updateMessage("Download Failed");
                    showError("Download Failed", "An error occurred while downloading the book.");
                    return null;
                }
                return null;
            }

            @Override
            protected void succeeded() {
                showInfo("Download Complete", "The book has been downloaded successfully.");
            }

            @Override
            protected void failed() {
                showError("Download Failed", "An error occurred while downloading the book.");
            }
        };

        // Run the task in the background
        new Thread(downloadTask).start();
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
