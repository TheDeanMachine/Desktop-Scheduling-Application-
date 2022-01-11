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

/**
 * This class is used to generate various reports.
 */
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
    private ComboBox<String> typeComboBox;

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

    /**
     * This method is used to return to the main (appointments) screen.
     * Uses the back button as the event trigger.
     */
    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(backButton, "/view/Appointments.fxml");
    }

    /**
     * This method displays results based on user selection.
     * When the user selects a contact from the combo-box, the tableview
     * is populated with appointments for that contact.
     */
    @FXML
    void onActionSelectContact(ActionEvent event) {
        // get users selection
        Contacts selectedContact = contactComboBox.getSelectionModel().getSelectedItem();
        int id = selectedContact.getContactId();
        // display the appointments corresponding to the contact
        reportsTableView.setItems(new AppointmentsDAO().findAppointmentByContactId(id));
    }

    /**
     * This method displays results based on the user selection.
     * When user selects a Month type from the first combo-box,
     * and then an appointment type from the second combo-box,
     * the results are displayed in label based on the results of those two.
     */
    @FXML
    void onActionDisplayResults(ActionEvent event) {
        // get the month selected
        Month month = monthPicker.getValue();

        // get the type selected
        String typeSelection = typeComboBox.getValue();

        // check for null in either combo box before calculating the results
        if(month == null || typeSelection == null) {
            return;
        } else {
            // convert to values
            int monthValue =  month.getValue();

            // call the method to calculate the results
            AppointmentsDAO result = new AppointmentsDAO();
            String text = String.valueOf(result.getResultsForReports(monthValue, typeSelection));

            // then display the results in the text string
            resultText.setText(text);
        }
    }

    /**
     * This method displays results based on user selection.
     * When the user selects a customer ID from the combo-box,
     * the results are displayed in a label based on the number of appointees the customer has.
     */
    @FXML
    void onActionDisplayTotalCustomers(ActionEvent event) {
        // one liner version of similar process in the method above
        resultText2.setText(String.valueOf(new AppointmentsDAO().
                findTotalAppointmentByCustomer(customerIdComboBox.getValue().getCustomerId())));
    }

    /**
     * Initialize Method.
     * This method is from the interface Initializable, and is overridden here.
     * The method is loaded(initialized) when this controller gets called by the display method in appointmentsScreen.
     * It contains instructions to set TableView and Combo Boxes with the data that they will be working with.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the contacts' combo box with contacts
        contactComboBox.setItems(new ContactsDAO().read());

        // set the contacts appointments' tableview with an empty list, until a contact has been selected
        ObservableList<Appointments> emptyList = FXCollections.observableArrayList();
        reportsTableView.setItems(emptyList);

        // set the contact appointments columns with the data
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTimeAsString"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTimeAsString"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        // set the type combo box with list of appointment types
        typeComboBox.setItems(new AppointmentsDAO().getListOfTypes());

        // set month list with month enums
        monthPicker.setItems(TimeHelper.getMonths());

        // set the customerId combo box with list of customers
        customerIdComboBox.setItems(new CustomerDAO().read());

    }
}
