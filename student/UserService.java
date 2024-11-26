package student;

import java.util.List;

import database.StudentLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import user.Student;

public class UserService {

    private static int loggedInStudentId; // Static variable to store the logged-in user's ID

    // Method to set logged-in student ID (this would be called when user logs in)
    public void setLoggedInStudentId(int studentId) {
        loggedInStudentId = studentId;
    }

    public List<Student> getLoggedInStudents() {
        List<Student> students = StudentLogin.fetchLoggedInStudents(loggedInStudentId);
        System.out.println("Fetched students: " + students.size());  // Debug line
        return students;
    }
    public static int addStudyGroup() {
        int groupID=StudentLogin.addStudyGroup();
        return groupID;
    }

    public static void addStudentToGroup(int studentId, int groupId)
    {
        StudentLogin.addStudentToGroup(studentId,groupId);
    }
    public static List<StudyGroup> getStudyGroupsForStudent(int ID)
    {
        List<StudyGroup> groups=StudentLogin.getStudyGroupsForStudent(ID);
        return groups;
    }

    
    
}
