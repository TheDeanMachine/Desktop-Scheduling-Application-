package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Reports extends SuperController implements Initializable  {

    /// Contact Reports Fields fx:id ///
    @FXML
    private TableView<Appointments> reportsTableView;

    @FXML
    private TableColumn<Appointments, Integer> appointmentIdColumn;

    @FXML
    private TableColumn<Appointments, String> titleColumn;

    @FXML
    private TableColumn<Appointments, String> descriptionColumn;

    @FXML
    private TableColumn<Appointments, String> typeColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> startColumn;

    @FXML
    private TableColumn<Appointments, LocalDateTime> endColumn;

    @FXML
    private TableColumn<Appointments, Integer> customerIdColumn;

    @FXML
    private ComboBox<Contacts> contactComboBox;

    /// Month/Type Reports Fields fx:id ///

    @FXML
    private DatePicker MonthDatePicker;

    @FXML
    private ComboBox<Appointments> typeComboBox;

    @FXML
    private Label ResultText;


    /// Customer Reports Fields fx:id ///
    @FXML
    private TableView<Customers> customerContactInformationTableView;

    @FXML
    private TableColumn<Customers, Integer> customerIdReferenceColumn;

    @FXML
    private TableColumn<Customers, String> customerNameColumn;

    @FXML
    private TableColumn<Customers, String> customerPhoneColumn;


    /// Back
    @FXML
    private Button backButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(backButton, "/view/Appointments.fxml");
    }

    @FXML
    void onActionSelectContact(ActionEvent event) {
        // get users selection
        Contacts selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        int id = selectedContact.getContactId();
        // display the appointments corresponding to the contact
        reportsTableView.setItems(new AppointmentsDAO().findAppointmentByContactId(id));
        customerContactInformationTableView.setItems(new CustomerDAO().getCustomerContactInformation(id));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the contacts' combo box with contacts
        contactComboBox.setItems(new ContactsDAO().read());

        // set the contacts appointments' tableview with an empty list
        ObservableList<Appointments> emptyList = FXCollections.observableArrayList();
        reportsTableView.setItems(emptyList);

        // set customer contact information tableview with the empty list
        ObservableList<Customers> emptyList2 = FXCollections.observableArrayList();
        customerContactInformationTableView.setItems(emptyList2);

        // set the customer contact information columns with the data
        customerIdReferenceColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // set the contact appointments columns with the data
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        // set the type combo box with list of appointment types
        typeComboBox.setItems(new AppointmentsDAO().read());





    }
}
