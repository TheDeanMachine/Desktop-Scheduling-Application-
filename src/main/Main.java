package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.JDBC;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FirstScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        JDBC.getConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
