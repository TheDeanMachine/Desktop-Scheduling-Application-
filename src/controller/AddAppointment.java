package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UsersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import utilities.TimeHelper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

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
    private ComboBox<Appointments> typeComboBox;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private DatePicker appointmentDatePicker;

    @FXML
    private ComboBox<LocalTime> appointmentStartComboBox;

    @FXML
    private ComboBox<LocalTime> appointmentEndComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createAppointmentButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Appointments.fxml");
    }

    @FXML
    void onActionCreateAppointment(ActionEvent event) throws IOException {
        // collect information
        int customerId = customerIdComboBox.getValue().getCustomerId();
        int userId = userIdComboBox.getValue().getUserID();
        int contactId = contactComboBox.getValue().getContactId();
        String type = typeComboBox.getValue().getType();
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        LocalDate date = appointmentDatePicker.getValue();
        LocalDateTime start = LocalDateTime.of(date, appointmentStartComboBox.getValue());
        LocalDateTime end = LocalDateTime.of(date, appointmentEndComboBox.getValue());

        // create appointment object
        Appointments appointment = new Appointments(0, title, description, location, type, start, end,
                customerId, userId, contactId);

        // pass it to dao for insertion
        AppointmentsDAO dao = new AppointmentsDAO();
        dao.create(appointment);

        displayNewScreen(createAppointmentButton, "/view/Appointments.fxml");
    }

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
