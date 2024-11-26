package sda;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class WelcomeController {

      @FXML
    private ImageView gif1;

    //@FXML
    //private ImageView gif2;

    @FXML
    private Text welcomeText;

    public void initialize() {
        // Load the GIFs
        Image image1 = new Image(getClass().getResourceAsStream("assets/welcome_screen.gif"));
        //Image image2 = new Image(getClass().getResourceAsStream("assets/image2.gif"));

        gif1.setImage(image1);
        //gif2.setImage(image2);

        // Apply animation to the welcome text
        animateWelcomeText();

        // Transition to login/signup screen after a delay
        welcomeText.setOnMouseClicked(event -> transitionToAuth());
    }

    private void animateWelcomeText() {
        // Slide in animation
        TranslateTransition slideTransition = new TranslateTransition(Duration.seconds(5), welcomeText);
        slideTransition.setFromY(-50); // Start position
        slideTransition.setToY(0);    // End position

        // Fade in animation
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), welcomeText);
        fadeTransition.setFromValue(0); // Start invisible
        fadeTransition.setToValue(1);   // Fully visible

        // Play both animations together
        slideTransition.play();
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> transitionToAuth()); // After animation, move to auth screen
    }

    private void transitionToAuth() {
        try {
            App.setRoot("usertype"); // Replace with your login/signup screen FXML
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

