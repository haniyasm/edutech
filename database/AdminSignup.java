package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminSignup {

    public boolean registerAdmin(String username, String password) {
        String checkQuery = "SELECT COUNT(*) FROM admin";
        String insertQuery = "INSERT INTO admin (username, password) VALUES (?, ?)";

        try (Connection connection = DatabaseConnector.connect();
             PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {

            ////////this is somewhat the approach of singleton//////
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.err.println("Admin already exists. Signup denied.");
                return false;
            }

            insertStatement.setString(1, username);
            insertStatement.setString(2, password);

            int rowsAffected = insertStatement.executeUpdate();
            return rowsAffected > 0; // Returns true if insertion is successful
        } catch (Exception e) {
            System.err.println("Error during admin signup: " + e.getMessage());
            return false;
        }
    }
    
}
