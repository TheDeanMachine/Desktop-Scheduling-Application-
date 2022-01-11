package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UsersDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import utilities.TimeHelper;
import utilities.TimeSlot;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This class is used to add an appointment to the database.
 */
public class AddAppointment extends SuperController implements Initializable  {

    /// Appointment Fields fx:id ///
    @FXML
    private TextField appointmentIdText;

    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private ComboBox<Users> userIdComboBox;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<TimeSlot> appointmentStartComboBox;

    @FXML
    private ComboBox<TimeSlot> appointmentEndComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createAppointmentButton;

    /**
     * This method is used to return to the appointment screen, without making changes.
     * Uses the cancel button as the event trigger.
     */
    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Appointments.fxml");
    }

    /**
     * This method is used to collect information and create the appointment.
     * The user entries are collected in text fields and combo boxes, and then used to create the appointment object.
     * That object is then passed into the database and created with a unique appointment id.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionCreateAppointment(ActionEvent event) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        // collect information and check for nulls
        int customerId = 0;
        try {
            customerId = customerIdComboBox.getValue().getCustomerId();
            if(customerId == 0 || customerIdComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Customer_ID");
            errorAlert.setContentText("Customer_ID cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        int userId = 0;
        try {
            userId = userIdComboBox.getValue().getUserID();
            if(userId == 0 || userIdComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a User_ID");
            errorAlert.setContentText("User_ID cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        int contactId = 0;
        try {
            contactId = contactComboBox.getValue().getContactId();
            if(contactId == 0 || contactComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Contact_ID");
            errorAlert.setContentText("Contact_ID cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        String type = null;
        try {
            type = typeComboBox.getValue();
            if(type == null || typeComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select an appointment Type");
            errorAlert.setContentText("Appointment type cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        String title = null;
        try {
            title = titleText.getText();
            if(title == null || titleText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter an appointment Title");
            errorAlert.setContentText("Appointment title cannot be blank \n" +
                    "Please enter a title into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String description = null;
        try {
            description =  descriptionText.getText();
            if(description == null || descriptionText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter an appointment Description");
            errorAlert.setContentText("Appointment description cannot be blank \n" +
                    "Please enter a description into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String location = null;
        try {
            location = locationText.getText();
            if(location == null || locationText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter an appointment Location");
            errorAlert.setContentText("Appointment Location cannot be blank \n" +
                    "Please enter a location into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        LocalDate date = null;
        try {
            date = appointmentDatePicker.getValue();
            if(date == null){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Date");
            errorAlert.setContentText("Date cannot be blank \n" +
                    "Please select a choice from the Date Picker to continue");
            errorAlert.showAndWait();
            return;
        }

        LocalDateTime start = null;
        try {
            start = LocalDateTime.of(date, appointmentStartComboBox.getValue().getLocalTime());
            if(appointmentStartComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Start Time");
            errorAlert.setContentText("Time cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        LocalDateTime end = null;
        try {
            end = LocalDateTime.of(date, appointmentEndComboBox.getValue().getLocalTime());
            if(appointmentEndComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a End Time");
            errorAlert.setContentText("Time cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        // logical time check
        if(end.isBefore(start)){
            errorAlert.setHeaderText("Incorrect appointment duration!");
            errorAlert.setContentText("Appointment end time cannot be before start time");
            errorAlert.showAndWait();
            return;
        }

        // logical time check
        if(end.isEqual(start)){
            errorAlert.setHeaderText("Incorrect appointment duration!");
            errorAlert.setContentText("Appointment end time cannot be same as start time");
            errorAlert.showAndWait();
            return;
        }

        // create a list of appointments by a given customerId, in order to check against existing appointments
        ObservableList<Appointments> listOfApp = new AppointmentsDAO().findAppointmentByCustomerId(customerId);

        // overlapping appointment check
        for(Appointments app : listOfApp) {
            if(TimeHelper.checkAppointmentTime(app.getStart(), start, app.getEnd(), end)){
                errorAlert.setHeaderText("Overlap appointment!");
                errorAlert.setContentText("Appointment time conflicts with existing customer appointment");
                errorAlert.showAndWait();
                return;
            }
        }

        // create appointment object
        Appointments appointment = new Appointments(0, title, description, location, type, start, end,
                customerId, userId, contactId);

        // pass it to dao for insertion
        new AppointmentsDAO().create(appointment);

        displayNewScreen(createAppointmentButton, "/view/Appointments.fxml");
    }

    /**
     * Initialize Method.
     * This method is from the interface Initializable, and is overridden here.
     * The method is loaded(initialized) when this controller gets called by the display method in AppointmentScreen.
     * It contains instructions to set a Combo Boxes with the data they will be working with.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the combo boxes with data
        customerIdComboBox.setItems(new CustomerDAO().read());
        userIdComboBox.setItems(new UsersDAO().read());
        contactComboBox.setItems(new ContactsDAO().read());
        typeComboBox.setItems(new AppointmentsDAO().getListOfTypes());
        appointmentStartComboBox.setItems(TimeHelper.initializeTimeSlots());
        appointmentEndComboBox.setItems(TimeHelper.initializeTimeSlots());
    }
}
