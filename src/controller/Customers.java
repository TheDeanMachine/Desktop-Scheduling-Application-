package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class Customers extends SuperController {

    /// Customers TableView Fields fx:id ///
    @FXML
    private TableView<?> customersTableView;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private TableColumn<?, ?> customerNameColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> postalCodeColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> divisionIdColumn;

    @FXML
    private TableColumn<?, ?> countryColumn;

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

}
