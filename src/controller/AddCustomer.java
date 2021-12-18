package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddCustomer extends SuperController {

    /// Customer Fields fx:id ///
    @FXML
    private TextField customerIdText;

    @FXML
    private TextField customerNameText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private ComboBox<?> divisionComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createCustomerButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Customers.fxml");
    }

    @FXML
    void onActionCreateCustomer(ActionEvent event) throws IOException {

        //TODO
        // collect input information
        // set country/state combo box with list of there respected values
        // create a customer object with the collected data
        // call Create method to insert into the database



        displayNewScreen(createCustomerButton, "/view/Customers.fxml");
    }

}

