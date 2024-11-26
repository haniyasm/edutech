package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentSignup {
    public boolean registerStudent(String name, String username, String password) {
        String query = "INSERT INTO students (name, username, password) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if insertion is successful
        } catch (Exception e) {
            System.err.println("Error during student signup: " + e.getMessage());
            return false;
        }
    }
}
