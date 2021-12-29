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
    private Label signInLabel;

    @FXML
    private Label primaryLabel;

    @FXML
    private Label secondaryLabel;

    @FXML
    private Label userZoneLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

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
            // check for french
            if (Locale.getDefault().getLanguage().equals("fr")){
                errorAlert.setHeaderText("Identifiants incorrects");
                errorAlert.setContentText("Veuillez réessayer");
                // make "ok" button in French
                ButtonType okButton = new ButtonType("d'accord");
                errorAlert.getButtonTypes().setAll(okButton);
                errorAlert.showAndWait();
                return;
            } else {
                errorAlert.setHeaderText("Incorrect credentials");
                errorAlert.setContentText("Please try again");
                errorAlert.showAndWait();
                return;
            }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // displays user timezone
        ZoneId zone = ZoneId.systemDefault();
        String zoneDisplay = zone.getId();
        timezoneLabel.setText(zoneDisplay);

        // translate words on log in screen to french, when French region is active
        resourceBundle = ResourceBundle.getBundle("utilities/Localization_fr", Locale.getDefault());

        if (Locale.getDefault().getLanguage().equals("fr")){
            primaryLabel.setText(resourceBundle.getString("Desktop"));
            secondaryLabel.setText(resourceBundle.getString("for"));
            userZoneLabel.setText(resourceBundle.getString("your"));
            signInLabel.setText(resourceBundle.getString("sign"));
            userNameLabel.setText(resourceBundle.getString("user"));
            passwordLabel.setText(resourceBundle.getString("password"));
            logInButton.setText(resourceBundle.getString("log"));
            userNameText.setPromptText(resourceBundle.getString("user"));
            passwordText.setPromptText(resourceBundle.getString("password"));
        }
    }
}
