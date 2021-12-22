package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private ComboBox<?> appointmentStartComboBox;

    @FXML
    private ComboBox<?> appointmentEndComboBox;

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



        displayNewScreen(createAppointmentButton, "/view/Appointments.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
