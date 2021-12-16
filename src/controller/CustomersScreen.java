package controller;

import DAO.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomersScreen extends SuperController implements Initializable {

    /// Customers TableView Fields fx:id ///
    @FXML
    private TableView<Customers> customersTableView;

    @FXML
    private TableColumn<Customers, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customers, String> customerNameColumn;

    @FXML
    private TableColumn<Customers, String> addressColumn;

    @FXML
    private TableColumn<Customers, String> postalCodeColumn;

    @FXML
    private TableColumn<Customers, String> phoneColumn;

    @FXML
    private TableColumn<Customers, String> divisionIdColumn;

    @FXML
    private TableColumn<Customers, String> countryColumn;

    /// Customers Button Fields fx:id ///
    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(backButton, "/view/Appointments.fxml");
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws IOException {

    }

    @FXML
    void onActionOpenAddForm(ActionEvent event) throws IOException {
        displayNewScreen(addButton, "/view/AddCustomer.fxml");
    }

    @FXML
    void onActionOpenModifyFrom(ActionEvent event) throws IOException {
        displayNewScreen(modifyButton, "/view/ModifyCustomer.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set the customers' tableview with the data it will be working with
        customersTableView.setItems(new CustomerDAO().read());

        // set the columns with the data
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

    }
}
