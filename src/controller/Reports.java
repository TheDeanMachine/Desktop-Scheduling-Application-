package controller;

import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private ComboBox<?> monthComboBox;

    @FXML
    private ComboBox<Appointments> typeComboBox;

    @FXML
    private ListView<String> resultListView1;

    /// Customer Reports Fields fx:id ///
    @FXML
    private ComboBox<Customers> customerIdComboBox;

    @FXML
    private ListView<String> resultListView2;

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
    void onActionSelectCustomerId(ActionEvent event) {
        // get users selection
        Customers customer = customerIdComboBox.getSelectionModel().getSelectedItem();
        int id = customer.getCustomerId();
        String phone = CustomerDAO.getCustomerPhoneNumber(id);
        // set customers list results based on user selection
        //resultListView2.setItems(phone);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the contacts' combo box with contacts
        contactComboBox.setItems(new ContactsDAO().read());

        // set the contacts appointments' tableview with an empty list
        ObservableList<Appointments> emptyList = FXCollections.observableArrayList();
        reportsTableView.setItems(emptyList);

        // set the columns with the data they will be working with
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        // set the type combo box with list of appointmetn types
        typeComboBox.setItems(new AppointmentsDAO().read());

        // set the customers' combo box with customers names list
        customerIdComboBox.setItems(new CustomerDAO().read());



    }
}
