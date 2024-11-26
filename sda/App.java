package sda;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.Student;
import user.Student;
import user.parent;
import user.Admin;
import user.User;
import java.io.IOException;
import java.util.ArrayList;


import courses.Course;

import java.net.URL;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static String userType;
    public static ArrayList<User> students = new ArrayList<>(); //for active students
    public static ArrayList<User> parents = new ArrayList<>();  //for active parents
    public static ArrayList<Course> courses = new ArrayList<>();
    private static Admin a;

    public static int studentActiveCount=0;
    public static Student currentStudent; // Logged-in student

    public static ArrayList<User> getStudents() {
        return students;
    }

    public static ArrayList<User> getParents() {
        return parents;
    }

    @Override
    public void start(Stage stage) throws IOException {
        a = Admin.getInstance();
    	adminAddCourses();
        scene = new Scene(loadFXML("welcome"), 1100, 810);
        stage.setScene(scene);
        scene.getStylesheets().add(App.class.getResource("style/welcome.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("style/usertype.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("style/auth.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("style/studentMenu.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("style/ParentTrackPerformance.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("style/StudentTrackPerformance.css").toExternalForm());
        scene.getStylesheets().add(App.class.getResource("style/listviewstyles.css").toExternalForm());
        //scene.getStylesheets().add(App.class.getResource("style/studentPlan.css").toExternalForm());
        
        URL cssUrl = getClass().getResource("/sda/studentPlan.fxml");
if (cssUrl != null) {
    System.out.println("CSS File Found at: " + cssUrl);
} else {
    System.err.println("CSS File NOT Found!");
}

   

        
        

        
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        //scene.setRoot(loadFXML(fxml));

        try {
            System.out.println("Loading FXML: " + fxml);
            scene.setRoot(loadFXML(fxml));
            System.out.println("FXML loaded successfully: " + fxml);
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + fxml);
            e.printStackTrace();
        }
    }

    public static void setUserType(String type) {
        userType = type;
    }

    public static String getUserType() {
        return userType;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        
        // Correct path for loading FXML resources from classpath
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void adminAddCourses() {
    	a.addCourses(courses);
        //currentStudent.setCourses(courses);
        if (currentStudent!=null)
        {
            currentStudent.setCourses(courses);
        }
    	System.out.println("Courses Added Successfully!");
    	
    	for (int i = 0; i < courses.size(); i++) {
            System.out.println("Course " + (i + 1) + ": " + courses.get(i));
        }
    }

    

    public static void main(String[] args) {
        launch();
    }

}