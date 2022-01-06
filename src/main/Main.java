package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;
import java.util.Locale;

/**
 * This class creates and launches the application.
 */
public class Main extends Application {

    /**
     * Overridden start method sets the root stage for the JavaFx project.
     * The starting point of the application is in the LogIn Screen.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Main method, the starting point of the application.
     * The commented out method is used to test the language changes feature in the LogIn screen.
     * There are two calls to JDBC, one establishes a connection with the database prior to launching the application,
     * and the other closes the connection.
     * The launch method launches the JavaFx project.
     */
    public static void main(String[] args) {
//        // test language change
//        Locale.setDefault((new Locale("fr")));

        JDBC.getConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
