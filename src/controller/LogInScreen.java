package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInScreen extends SuperController implements Initializable {

    @FXML
    private Label desktop;

    @FXML
    private Label timezoneLabel;

    @FXML
    private TextField userNameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button logInButton;

    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String userName = null;
        String password = null;
        boolean logInAttempt = false;
        int counter = 0;

        try {
            userName = userNameText.getText();
            password = passwordText.getText();

            if ((userName == null || userName.isBlank() || !userName.equals("sqlUser")) || // username wrong
             (password == null || password.isBlank() || !password.equals("Passw0rd!"))) {  // password wrong
                counter++;
                userActivity(counter, logInAttempt);
                throw new Exception();
            } else {
                counter++;
                logInAttempt = true;
                userActivity(counter, logInAttempt);
            }

        } catch (Exception e) {
            errorAlert.setHeaderText("Incorrect credentials");
            errorAlert.setContentText("Please try again");
            errorAlert.showAndWait();
            return;
        }

        displayNewScreen(logInButton, "/view/Appointments.fxml");
    }

    // user log-in attempts
    public void userActivity(int count, boolean attempt) throws IOException {
        LocalDateTime time = LocalDateTime.now();

        // try-with-resources, auto closes
        try (PrintWriter pw = new PrintWriter(new FileWriter("login_activity.txt", true))){
            pw.println("Attempts made: " + count);
            pw.println("The time and date of attempt was: " + time);
            pw.println("Successful log in: " + attempt);
            pw.println("---------------------");
        }
    }


    public static void main(String[] args) {

//        ResourceBundle resourceBundle = ResourceBundle.getBundle("utilities/Localization_fr.properties",
//                Locale.getDefault());
//
//        if (Locale.getDefault().getLanguage().equals("fr")){
//            System.out.println(resourceBundle.getString("Desktop") +
//                    resourceBundle.getString("Scheduling") +
//                    resourceBundle.getString("Application")
//            );
//        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        String zoneDisplay = zone.getId();
        timezoneLabel.setText(zoneDisplay);

//
//
//        ResourceBundle resourceBundle2 = ResourceBundle.getBundle("utilities/Localization_fr.properties",
//                Locale.getDefault());
//
//        if (Locale.getDefault().getLanguage().equals("fr")){
//            desktop.setText(resourceBundle2.getString("Desktop"));
//
//        }


    }
}
