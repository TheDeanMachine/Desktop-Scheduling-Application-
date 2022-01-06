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
import utilities.TimeHelper;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;

public class Reports extends SuperController implements Initializable {

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
    private ComboBox<Month> monthPicker;

    @FXML
    private ComboBox<Appointments> typeComboBox;

    @FXML
    private Label resultText;

    /// Customer Reports Fields fx:id ///
    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private Label resultText2;


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
    }

    @FXML
    void onActionDisplayResults(ActionEvent event) {
        // get the month selected
        Month month = monthPicker.getValue();

        // get the type selected
        Appointments typeSelection = typeComboBox.getSelectionModel().getSelectedItem();

        // check for null in either combo box before calculating hte results
        if(month == null || typeSelection == null) {
            return;
        } else {
            // convert to values
            String selectedType = typeSelection.getType();
            int monthValue =  month.getValue();

            // call the method to calculate the results
            AppointmentsDAO result = new AppointmentsDAO();
            String text = String.valueOf(result.getResultsForReports(monthValue, selectedType));

            // then display the results in the text string
            resultText.setText(text);
        }
    }

    @FXML
    void onActionDisplayTotalCustomers(ActionEvent event) {
        // one liner version of similar process in the method above
        resultText2.setText(String.valueOf(new AppointmentsDAO().
                findTotalAppointmentByCustomer(customerIdComboBox.getValue().getCustomerId())));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the contacts' combo box with contacts
        contactComboBox.setItems(new ContactsDAO().read());

        // set the contacts appointments' tableview with an empty list
        ObservableList<Appointments> emptyList = FXCollections.observableArrayList();
        reportsTableView.setItems(emptyList);

        // set the contact appointments columns with the data
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        // set the type combo box with list of appointment types
        typeComboBox.setItems(new AppointmentsDAO().getListOfTypes());

        // set month list with month enums
        monthPicker.setItems(TimeHelper.getMonths());

        // set the customerId combo box with list of customers
        customerIdComboBox.setItems(new CustomerDAO().read());

    }
}
