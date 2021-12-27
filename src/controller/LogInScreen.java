package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInScreen extends SuperController implements Initializable {

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
        String userName = null; // sqlUser
        String password = null; // Passw0rd!
        boolean logInAttempt = false;
        int counter = 1;

//        try {
//            userName = userNameText.getText();
//            if (userName == null || userName.isBlank() || !userName.equals("sqlUser")) {
//                throw new Exception();
//            }
//        } catch (Exception e) {
//            errorAlert.setHeaderText("Incorrect user name");
//            errorAlert.setContentText("Please try again");
//            errorAlert.showAndWait();
//            return;
//        }
//
//        // password input verification
//        try {
//            password = passwordText.getText();
//            if (password == null || password.isBlank() || !password.equals("Passw0rd!")) {
//                throw new Exception();
//            }
//        } catch (Exception e) {
//            errorAlert.setHeaderText("Incorrect password");
//            errorAlert.setContentText("Please try again");
//            errorAlert.showAndWait();
//            return;
//        }

        try {
            userName = userNameText.getText();
            password = passwordText.getText();


            if ((userName == null || userName.isBlank() || !userName.equals("sqlUser")) ||
             (password == null || password.isBlank() || !password.equals("Passw0rd!"))) {
                counter++;
                userActivity(counter, logInAttempt);
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Incorrect credentials");
            errorAlert.setContentText("Please try again");
            errorAlert.showAndWait();
            return;
        }

        logInAttempt = true;
        userActivity(counter, logInAttempt);

        displayNewScreen(logInButton, "/view/Appointments.fxml");
    }

    // user log-in attempts
    public void userActivity(int count, boolean attempt) throws IOException {
        LocalDateTime time = LocalDateTime.now();

        // try-with-resources, auto closes
        try (PrintWriter pw = new PrintWriter(new FileWriter("login_activity.txt"))){
            pw.println(count);
            pw.println(time);
            pw.println(attempt);
        }
    }



    public static void main(String[] args) {
        // test
        Locale.setDefault((new Locale("fr")));

        ResourceBundle resourceBundle = ResourceBundle.getBundle("/utilities/Localization_fr.properties",
                Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")){
            System.out.println("Desktop Scheduling Application ");
        }

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        String zoneDisplay = zone.getId();
        timezoneLabel.setText(zoneDisplay);
    }
}
