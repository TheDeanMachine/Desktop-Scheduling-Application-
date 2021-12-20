package controller;

import DAO.CountriesDAO;
import DAO.CustomerDAO;
import DAO.DataAccessObject;
import DAO.FirstLevelDivisionsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Countries;
import model.Customers;
import model.FirstLevelDivisions;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomer extends SuperController implements Initializable {

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
    private ComboBox<Countries> countryComboBox;

    @FXML
    private ComboBox<FirstLevelDivisions> divisionComboBox;

    @FXML
    private Button cancelButton;

    @FXML
    private Button createCustomerButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Customers.fxml");
    }


    @FXML
    void onActionFilterDivisionsByCountry(ActionEvent event) {
        Countries countrySelection = countryComboBox.getSelectionModel().getSelectedItem();
        int countryId = countrySelection.getCountryId();
        divisionComboBox.setItems(new FirstLevelDivisionsDAO().getDivisionsByCountryId(countryId));

    }

    @FXML
    void onActionCreateCustomer(ActionEvent event) throws IOException {
        // collect input information
        String name = customerNameText.getText();
        String phone = phoneNumberText.getText();
        String address = addressText.getText();
        String postal = postalCodeText.getText();
        FirstLevelDivisions divisions = divisionComboBox.getSelectionModel().getSelectedItem();
        int divisionId = divisions.getDivisionId();

        // create a customer object with the collected data
        Customers newCustomer = new Customers(0, name, address, postal, phone, divisionId);

        // call create method to insert into the database
        CustomerDAO dao = new CustomerDAO();
        dao.create(newCustomer);

        displayNewScreen(createCustomerButton, "/view/Customers.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set combo box with values
        countryComboBox.setItems(new CountriesDAO().read());
    }
}

