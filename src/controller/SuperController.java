package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;

/**
 * The SuperController Class is the Super class to multiple controller Sub classes.
 * Provides a single method for switching screens.
 */
public abstract class SuperController {
    // Reference Variables
    private Stage stage;
    private Parent root;

    /**
     * Method for switching screens on button clicks.
     * @param node the placement holder for the type of button being passed in.
     * @param locationString the location of the next screen.
     */
    public void displayNewScreen(Node node, String locationString) throws IOException {
        stage = (Stage)(node).getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(locationString));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
