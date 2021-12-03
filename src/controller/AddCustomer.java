package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddCustomer {

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
    void onActionBackToMain(ActionEvent event) {

    }

    @FXML
    void onActionCreateCustomer(ActionEvent event) {

    }

}

