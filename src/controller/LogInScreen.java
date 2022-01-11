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
import java.util.function.Consumer;

/**
 * Log in screen, contains methods for application access.
 *
 */
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

    // holds the difference in time between appointments
    public static long timeDifference;

    // used to check if an appointment within 15 has been found
    private static boolean found = false;

    // holds the user id of the logged-in user
    private static int userId;

    /**
     * Sets the userId variable with value passed in.
     * @param id the value passed in, used to set userId.
     */
    public static void holdId(int id){
        userId = id;
    }


    /**
     * LAMBDA.
     * This lambda is used to check the difference in time between the current time, and the time passed in.
     * It allows for better code flow, and readability.
     */
    TimeCheck check = timeToCheck -> {
        timeDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), timeToCheck);
        if(timeDifference > 0 && timeDifference < 16 ) {
            return true;
        }
        return false;
    };

    /**
     * Used to check for any upcoming appointments for a given user.
     * Method creates a list of appointments based on the userID of the logging in user.
     * It checks that list against the current time and notifies the user if there are any
     * appointments within 15 min.
     *
     * LAMBDA.
     * The use of the consumer lambda function improves the code flow, and allows for easier iteration
     * and checking for the given list of appointments.
     */
    public void checkForUpcomingAppointments() {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        // get list appointments for given user id
        ObservableList<Appointments> listOfApp = new AppointmentsDAO().findAppointmentByUserId(userId);

//        // check those appointments within 15 min of that user // prior to lambda
//        for(Appointments app : listOfApp) {
//            if(TimeHelper.checkForAppointmentsWithin15(app.getStart())) { // if listOfApp contains an upcoming app
//                alertInfo.setHeaderText(
//                        "Upcoming appointment ID #" + app.getAppointmentId() + " for " + "\n" +
//                                app.getStartTimeAsString() + "\n" +
//                                "Starts in " + TimeHelper.timeDifference + " minutes");
//                alertInfo.setContentText("Press ok to continue");
//                alertInfo.showAndWait();
//                found = true;
//                return;
//            }
//        }

        // check those appointments within 15 min of that user
        listOfApp.forEach(appointments -> {
            if(check.checkForAppointmentsWithin15(appointments.getStart())) { // if listOfApp contains an upcoming app
                alertInfo.setHeaderText(
                        "Upcoming appointment ID #" + appointments.getAppointmentId() + " for " + "\n" +
                                appointments.getStartTimeAsString() + "\n" +
                                "Starts in " + timeDifference + " minutes");
                alertInfo.setContentText("Press ok to continue");
                alertInfo.showAndWait();
                found = true;
                return;
            }
        });

        if(!found) { // if listOfApp does not contain an upcoming app
            alertInfo.setHeaderText("No upcoming appointments found");
            alertInfo.setContentText("Press ok to continue");
            alertInfo.showAndWait();
            return;
        }

    }

    /**
     * This method is used to verify credentials and log the user in.
     * The user input is collected and compared against the users in the database.
     * If there is match, the user is logged in, else is presented with error.
     * @throws IOException catches input and output errors.
     */
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
                userActivity(false, userName);
                throw new Exception();

            } else if(!UsersDAO.checkForUser(userName, password)){
                userActivity(false, userName);
                throw new Exception();

            } else {
                checkForUpcomingAppointments();
                userActivity(true, userName);
            }

        } catch (Exception e) {
            // check for french
            if(Locale.getDefault().getLanguage().equals("fr")){ // custom error alert for french
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


    /**
     * This method is used to document the user log in attempts.
     * The attempts are written to a file, with corresponding information.
     * @param attempt variable representing the status of successful log in.
     */
    public void userActivity(boolean attempt, String user) throws IOException {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        // try-with-resources, auto closes
        try (PrintWriter pw = new PrintWriter(new FileWriter("login_activity.txt", true))){
            pw.println("Log in attempt for: " + user);
            pw.println("Attempt number: " + (++counter));
            pw.println("The date of attempt was: " + date);
            pw.println("The time of attempt was: " + time);
            pw.println("Successful log in was: " + attempt);
            pw.println("-------------------------------------------");
        }
    }

    /**
     * Initialize Method.
     * This method is from the interface Initializable, and is overridden here.
     * The method is loaded(initialized) when this controller gets called by the method in Main.
     * It contains instructions to configure the output of words used in the log in screen to French,
     * if that user happens to be in French region.
     * It also contains settings to display the users' timezone.
     */
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
