package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import student.StudentPlan;


public class UpdateStudentPlan {

    // CREATE: Add a new StudentPlan
    // CREATE: Add a new StudentPlan
// CREATE: Add a new StudentPlan
public static boolean addStudentPlan(int studentID, String name, int timeAvailable, int subjects, int chapters, String priority) {
    // Update the table name to "student_plan"
    String sql = "INSERT INTO student_plan (student_id, name, time_available, subjects, chapters, priority) VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, studentID);
        stmt.setString(2, name);
        stmt.setInt(3, timeAvailable);
        stmt.setInt(4, subjects);
        stmt.setInt(5, chapters);
        stmt.setString(6, priority);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Return true if the row is successfully inserted

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Updated method to fetch the student's plans from the database
public static List<StudentPlan> getStudentPlansByStudentID(int studentID) {
    String sql = "SELECT * FROM student_plan WHERE student_id = ?";
    List<StudentPlan> plans = new ArrayList<>();

    try (Connection conn = DatabaseConnector.connect();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, studentID);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            StudentPlan plan = new StudentPlan(
                    rs.getInt("student_id"),
                    rs.getInt("time_available"),
                    rs.getInt("subjects"),
                    rs.getInt("chapters"),
                    rs.getString("priority")
            );
            plans.add(plan);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return plans;
}



    // READ: Get all StudentPlans for a specific student
    /*

    // UPDATE: Update an existing StudentPlan
    public static boolean updateStudentPlan(int planID, int studentID, String name, int timeAvailable, int subjects, int chapters, String priority) {
        String sql = "UPDATE StudentPlan SET "
                   + "StudentID = ?, Name = ?, TimeAvailable = ?, Subjects = ?, Chapters = ?, Priority = ? "
                   + "WHERE PlanID = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, studentID);
            stmt.setString(2, name);
            stmt.setInt(3, timeAvailable);
            stmt.setInt(4, subjects);
            stmt.setInt(5, chapters);
            stmt.setString(6, priority);
            stmt.setInt(7, planID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE: Remove a StudentPlan by PlanID
    public static boolean deleteStudentPlan(int planID) {
        String sql = "DELETE FROM StudentPlan WHERE PlanID = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, planID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/
}
