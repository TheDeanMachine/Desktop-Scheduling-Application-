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
 * This class is used to modify the appointment in the database.
 */
public class ModifyAppointment extends SuperController implements Initializable {

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
    private Button updateAppointmentButton;

    /**
     * This method is used to return to the appointment screen, without making changes.
     * Uses the cancel button as the event trigger.
     */
    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Appointments.fxml");
    }

    /**
     * This method is used to collect information and modify the appointment.
     * The user entries are collected in text fields and combo boxes, and then used to create a new appointment object.
     * That object is then passed into the database and updated based on the appointment id.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        // collect information and check for nulls
        int appointmentId = Integer.parseInt(appointmentIdText.getText());
        int customerId = customerIdComboBox.getValue().getCustomerId();
        int userId = userIdComboBox.getValue().getUserID();
        int contactId = contactComboBox.getValue().getContactId();
        String type = typeComboBox.getValue();

        // only place where user could set a null in modify appointment
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
            if(appointmentDatePicker.getValue() == null){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Date");
            errorAlert.setContentText("Date cannot be blank \n" +
                    "Please select a choice from the Date Picker to continue");
            errorAlert.showAndWait();
            return;
        }

        // no null check required, once set in add appointment, cannot be made null
        LocalDateTime start = LocalDateTime.of(date, appointmentStartComboBox.getValue().getLocalTime());
        LocalDateTime end = LocalDateTime.of(date, appointmentEndComboBox.getValue().getLocalTime());

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
        ObservableList<Appointments> listOfApp = new AppointmentsDAO().findAppointmentByCustomerId(appointmentId, customerId);

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
        Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end,
                customerId, userId, contactId);

        // pass it to dao for updating
        new AppointmentsDAO().update(appointment);

        displayNewScreen(updateAppointmentButton, "/view/Appointments.fxml");
    }

    // holds the appointment object passed in by the modify button in appointments screen
    private static Appointments item = null;

    /**
     * Sets the item variable with the appointment object passed in, by the modify button in AppointmentScreen.
     * @param selectedAppointment the appointment object passed in.
     */
    public static void holdAppData(Appointments selectedAppointment) {
        item = selectedAppointment;
    }

    /**
     * Initialize Method.
     * This method is from the interface Initializable, and is overridden here.
     * The method is loaded(initialized) when this controller gets called by the display method in AppointmentScreen.
     * It contains instructions to set a Combo Boxes and text fields with the data they will be working with.
     * In Addition to that, it sets the appointment values based on the object passed in.
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

        // set the field values with the data passed in
        appointmentIdText.setText(String.valueOf(item.getAppointmentId()));
        customerIdComboBox.getSelectionModel().select(item.getCustomerObject());
        userIdComboBox.getSelectionModel().select(item.getUserObject());
        contactComboBox.getSelectionModel().select(item.getContactObject());
        typeComboBox.setValue(item.getType());
        titleText.setText(item.getTitle());
        descriptionText.setText(item.getDescription());
        locationText.setText(item.getLocation());
        appointmentDatePicker.setValue(item.getStart().toLocalDate());

        for(TimeSlot t : appointmentStartComboBox.getItems()){
            if(item.getStart().toLocalTime().equals(t.getLocalTime())){
                appointmentStartComboBox.setValue(t);
                break;
            }
        }

        for(TimeSlot t : appointmentEndComboBox.getItems()){
            if(item.getEnd().toLocalTime().equals(t.getLocalTime())){
                appointmentEndComboBox.setValue(t);
                break;
            }
        }

    }
}
