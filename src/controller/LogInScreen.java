package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

        // username input verification
        try {
            userName = userNameText.getText();
            if(userName == null || userName.isBlank() || !userName.equals("sqlUser") ) {
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Incorrect user name");
            errorAlert.setContentText("Please try again");
            errorAlert.showAndWait();
            return;
        }

        // password input verification
        try {
            password = passwordText.getText();
            if(password == null || password.isBlank() || !password.equals("Passw0rd!") ) {
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Incorrect password");
            errorAlert.setContentText("Please try again");
            errorAlert.showAndWait();
            return;
        }

        displayNewScreen(logInButton, "/view/Appointments.fxml");
    }

    public static void main(String[] args) {
//        // test
//        Locale.setDefault((new Locale("fr")));

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
