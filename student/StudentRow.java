package student;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class StudentRow {
    private final IntegerProperty id;
    private final StringProperty username;
    private final Button actionButton;

    public StudentRow(int id, String username, Button actionButton) {
        this.id = new SimpleIntegerProperty(id);
        this.username = new SimpleStringProperty(username);
        this.actionButton = actionButton;
    }

    // Getter for id
    public int getId() {
        return id.get();
    }

    // JavaFX property getter for id
    public IntegerProperty idProperty() {
        return id;
    }

    // Getter for username
    public String getUsername() {
        return username.get();
    }

    // JavaFX property getter for username
    public StringProperty usernameProperty() {
        return username;
    }

    // Getter for the action button
    public Button getActionButton() {
        return actionButton;
    }
}
