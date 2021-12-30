package controller;

import DAO.CountriesDAO;
import DAO.CustomerDAO;
import DAO.FirstLevelDivisionsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        // collect input information and check for nulls
        String name = null;
        try {
            name = customerNameText.getText();
            if(name == null || customerNameText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter a customer Name");
            errorAlert.setContentText("Customer name cannot be blank \n" +
                    "Please enter a customer name into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String phone = null;
        try {
            phone = phoneNumberText.getText();
            if(phone == null || phoneNumberText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter a customer Phone Number");
            errorAlert.setContentText("Customer phone number cannot be blank \n" +
                    "Please enter a customer phone number into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String address = null;
        try {
            address = addressText.getText();
            if(address == null || addressText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter a customer Address");
            errorAlert.setContentText("Customer address cannot be blank \n" +
                    "Please enter a customer address into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        String postal = null;
        try {
            postal =  postalCodeText.getText();
            if(postal == null || postalCodeText.getText().isBlank()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please enter a customer Postal Code");
            errorAlert.setContentText("Customer postal code cannot be blank \n" +
                    "Please enter a customer postal code into the text field to continue");
            errorAlert.showAndWait();
            return;
        }

        try {
            if(countryComboBox.getValue() == null || countryComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a Country");
            errorAlert.setContentText("Country cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

        int divisionId = 0;
        try {
            divisionId =   divisionComboBox.getSelectionModel().getSelectedItem().getDivisionId();
            if(divisionId == 0 || divisionComboBox.getSelectionModel().isEmpty()){
                throw new Exception();
            }
        } catch (Exception e) {
            errorAlert.setHeaderText("Please select a States/Province");
            errorAlert.setContentText("States/Provinces cannot be blank \n" +
                    "Please select a choice from the combo box to continue");
            errorAlert.showAndWait();
            return;
        }

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

