package database;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ParentSignup {
    public boolean registerParent(String username, String password, String name) {
        String query = "INSERT INTO parents (username, password, name) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, name); // Store the parent's name
    
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if insertion is successful
        } catch (Exception e) {
            System.err.println("Error during parent signup: " + e.getMessage());
            return false;
        }
    }
}
