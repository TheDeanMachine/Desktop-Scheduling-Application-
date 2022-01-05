package controller;

import DAO.AppointmentsDAO;
import DAO.UsersDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;
import utilities.TimeCheck;
import utilities.TimeHelper;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    // keeps track of log in attempts for a given instance
    private static int counter = 0;

    // holds the different in time between appointments
    public static long timeDifference;

    // holds the user id of the logged-in user
    private static int userId;

    public static void holdId(int id){
        userId = id;
    }


    TimeCheck check = timeToCheck -> {
        timeDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), timeToCheck);
        if(timeDifference > 0 && timeDifference < 16 ) {
            return true;
        }
        return false;
    };

    public void checkForUpcomingAppointments(){
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        boolean found = false;

        // get list appointments for given user id
        ObservableList<Appointments> listOfApp = new AppointmentsDAO().findAppointmentByUserId(userId);

        // check those appointments within 15 min of that user
        for(Appointments app : listOfApp) {
            if(check.checkForAppointmentsWithin15(app.getStart())) { // if listOfApp contains an upcoming app
                alertInfo.setHeaderText(
                        "Upcoming appointment ID #" + app.getAppointmentId() + " for " + "\n" +
                                app.getStartTimeAsString() + "\n" +
                                "Starts in " + timeDifference + " minutes");
                alertInfo.setContentText("Press ok to continue");
                alertInfo.showAndWait();
                found = true;
                return;
            }
        }

        if(!found) { // if listOfApp does not contain an upcoming app
            alertInfo.setHeaderText("No upcoming appointments found");
            alertInfo.setContentText("Press ok to continue");
            alertInfo.showAndWait();
            return;
        }

    }

    @FXML
    void onActionDisplayMainScreen(ActionEvent event) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        String userName = null;
        String password = null;

        try {
            userName = userNameText.getText().trim();
            password = passwordText.getText().trim();

            // check for credentials
            if (userName.isBlank() || password.isBlank()) {
                userActivity(false);
                throw new Exception();

            } else if(!UsersDAO.checkForUser(userName, password)){
                userActivity(false);
                throw new Exception();

            } else {
                checkForUpcomingAppointments();
                userActivity(true);
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
    public void userActivity(boolean attempt) throws IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        // try-with-resources, auto closes
        try (PrintWriter pw = new PrintWriter(new FileWriter("login_activity.txt", true))){
            pw.println("Attempt number: " + (++counter));
            pw.println("The date of attempt was: " + date);
            pw.println("The time of attempt was: " + time);
            pw.println("Successful log in was: " + attempt);
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
