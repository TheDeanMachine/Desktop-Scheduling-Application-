package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Contacts;

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

    /// Month/Type Reports Fields fx:id ///
    @FXML
    private ComboBox<Contacts> contactComboBox;

    @FXML
    private ComboBox<?> monthComboBox;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private ListView<?> resultListView1;

    /// Country/Division Reports Fields fx:id ///
    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ComboBox<?> divisionComboBox;

    @FXML
    private ListView<?> resultListView2;

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
        // set the appointments' tableview with the data it will be working with
        reportsTableView.setItems(new AppointmentsDAO().findByContactId(id));




    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // set the contacts' combo box with contacts
        contactComboBox.setItems(new ContactsDAO().read());



        // set the appointments' tableview with the data it will be working with
        reportsTableView.setItems(new AppointmentsDAO().read());

        // set the columns with the data
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));






    }
}
