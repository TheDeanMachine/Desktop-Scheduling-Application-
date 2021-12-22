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
        // collect input information
        Customers selectedCustomer = customerIdComboBox.getValue();
        int customerId = selectedCustomer.getCustomerId();

        Users selectedUser = userIdComboBox.getValue();
        int userId = selectedUser.getUserID();

        Contacts selectedContact = contactComboBox.getValue();
        int contactId = selectedContact.getContactId();

        Appointments selectedType = typeComboBox.getValue();
        String type = selectedType.getType();

        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();

        LocalDate appDay = appointmentDatePicker.getValue();
        LocalTime startTime = appointmentStartComboBox.getValue();
        LocalTime endTime = appointmentEndComboBox.getValue();

        LocalDateTime start = LocalDateTime.of(appDay, startTime);
        LocalDateTime end = LocalDateTime.of(appDay, endTime);

        Appointments appointment = new Appointments(0, title, description, location, type, start, end,
                customerId, userId, contactId);

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
