package database;


import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Testing database connection using DatabaseConnector...");

        try (Connection connection = DatabaseConnector.connect()) {
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("Error while closing the connection:");
            e.printStackTrace();
        } catch (RuntimeException e) {
            System.out.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }
}

