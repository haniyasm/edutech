package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import student.StudyGroup;
import user.Student;

public class StudentLogin {
    public StudentLogin(){};
    public boolean validate(String username, String password) {
        String query = "SELECT * FROM students WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if login is successful
        } catch (Exception e) {
            System.err.println("Error during student login: " + e.getMessage());
            return false;
        }
    }

    public Map<String, Object> getStudentInfo(String username) {
        Map<String, Object> studentInfo = new HashMap<>();
        String query = "SELECT * FROM students WHERE username = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            // Set the name and username parameters
            
            preparedStatement.setString(1, username);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                // Populate the map with student data
                studentInfo.put("student_id", resultSet.getInt("student_id"));
                studentInfo.put("name", resultSet.getString("name"));
                studentInfo.put("username", resultSet.getString("username"));
                studentInfo.put("password", resultSet.getString("password")); // Use cautiously
                studentInfo.put("number_of_courses", resultSet.getInt("number_of_courses"));
                studentInfo.put("number_of_quizzes_attempted", resultSet.getInt("number_of_quizzes_attempted"));
                studentInfo.put("number_of_assignments_attempted", resultSet.getInt("number_of_assignments_attempted"));
                studentInfo.put("created_at", resultSet.getTimestamp("created_at"));
            } else {
                return null; // No student found
            }
        } catch (Exception e) {
            System.err.println("Error fetching student info: " + e.getMessage());
            return null;
        }
        return studentInfo;
    }

    public Map<String, Object> getChildInfo(int stdID) {
        Map<String, Object> studentInfo = new HashMap<>();
        String query = "SELECT * FROM students WHERE student_id = ?";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            // Set the name and username parameters
            
            preparedStatement.setInt(1, stdID);
    
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                // Populate the map with student data
                studentInfo.put("student_id", resultSet.getInt("student_id"));
                studentInfo.put("name", resultSet.getString("name"));
                studentInfo.put("username", resultSet.getString("username"));
                studentInfo.put("password", resultSet.getString("password")); // Use cautiously
                studentInfo.put("number_of_courses", resultSet.getInt("number_of_courses"));
                studentInfo.put("number_of_quizzes_attempted", resultSet.getInt("number_of_quizzes_attempted"));
                studentInfo.put("number_of_assignments_attempted", resultSet.getInt("number_of_assignments_attempted"));
                studentInfo.put("created_at", resultSet.getTimestamp("created_at"));
            } else {
                return null; // No student found
            }
        } catch (Exception e) {
            System.err.println("Error fetching student info: " + e.getMessage());
            return null;
        }
        return studentInfo;
    }

    ///////////////Student List to Send to Information Expert: student.UserService.java///////////////
    public static List<Student> fetchLoggedInStudents(int loggedInStudentId) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students WHERE student_id != ?";  // Exclude the logged-in student by their ID
        
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
    
            // Set the logged-in student ID as a parameter
            statement.setInt(1, loggedInStudentId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Create a Student object with the correct columns
                    Student student = new Student(
                            resultSet.getInt("student_id"),  // student_id is the correct column name
                            resultSet.getString("name"),
                            resultSet.getString("username")
                    );
                    students.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching students: " + e.getMessage());
        }
    
        return students;
    }


    ///////////////for Adding Groups////////////////
    public static int addStudyGroup() {
        Connection connection = null;
        PreparedStatement insertGroupStmt = null;
        PreparedStatement updateGroupStmt = null;
        ResultSet resultSet = null;
        int newGroupId = -1;

        try {
            // Establish connection
            connection = DatabaseConnector.connect();
            
            // Step 1: Insert a new row into study_groups table (group_name will be NULL initially)
            String insertGroupSQL = "INSERT INTO study_groups (group_name) VALUES (NULL)";
            insertGroupStmt = connection.prepareStatement(insertGroupSQL, Statement.RETURN_GENERATED_KEYS);
            insertGroupStmt.executeUpdate();
            
            // Get the generated study_group_id
            resultSet = insertGroupStmt.getGeneratedKeys();
            if (resultSet.next()) {
                newGroupId = resultSet.getInt(1);
            }

            // Step 2: Generate the group_name based on the new group_id
            String groupName = "Study Group " + newGroupId;
            
            // Step 3: Update the study group with the generated group_name
            String updateGroupSQL = "UPDATE study_groups SET group_name = ? WHERE study_group_id = ?";
            updateGroupStmt = connection.prepareStatement(updateGroupSQL);
            updateGroupStmt.setString(1, groupName);
            updateGroupStmt.setInt(2, newGroupId);
            updateGroupStmt.executeUpdate();
            
            // Return the new study group ID
            return newGroupId;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (insertGroupStmt != null) insertGroupStmt.close();
                if (updateGroupStmt != null) updateGroupStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1; // Return -1 in case of failure
    }

    public static void addStudentToGroup(int studentId, int groupId) {
        Connection connection = null;
        PreparedStatement insertStmt = null;

        try {
            // Establish connection
            connection = DatabaseConnector.connect();;

            // Insert a record into student_studyGroup to link the student to the study group
            String insertSQL = "INSERT INTO student_studyGroup (student_id, study_group_id) VALUES (?, ?)";
            insertStmt = connection.prepareStatement(insertSQL);
            insertStmt.setInt(1, studentId);
            insertStmt.setInt(2, groupId);
            insertStmt.executeUpdate();

            System.out.println("Student " + studentId + " added to Study Group " + groupId);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (insertStmt != null) insertStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    ///////for retrieving all the groups student is a part of///////
    public static List<StudyGroup> getStudyGroupsForStudent(int studentId) {
    List<StudyGroup> studyGroups = new ArrayList<>();
    String sql = "SELECT sg.study_group_id, sg.group_name " +
                 "FROM student_studygroup ssg " +
                 "JOIN study_groups sg ON ssg.study_group_id = sg.study_group_id " +
                 "WHERE ssg.student_id = ?";

    try (Connection connection = DatabaseConnector.connect();
         PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, studentId); // Set the logged-in student's ID
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int groupId = resultSet.getInt("study_group_id");
            String groupName = resultSet.getString("group_name");
            studyGroups.add(new StudyGroup(groupId, groupName));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return studyGroups;
}

        

        
    
    

    
}
