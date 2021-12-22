package controller;

import DAO.CountriesDAO;
import DAO.CustomerDAO;
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

public class ModifyCustomer extends SuperController implements Initializable {

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
    private Button updateCustomerButton;

    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Customers.fxml");
    }

    @FXML
    void onActionFilterDivisionsByCountry(ActionEvent event) {
        // if country is selected clear the division preset selection, is triggered by this method
        divisionComboBox.getSelectionModel().clearSelection(); // doesn't seem to work??

        // get the selected country, and filter divisions by it
        Countries countrySelection = countryComboBox.getSelectionModel().getSelectedItem();
        int countryId = countrySelection.getCountryId();
        divisionComboBox.setItems(new FirstLevelDivisionsDAO().getDivisionsByCountryId(countryId));
    }

    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        // collect input information
        int id = Integer.parseInt(customerIdText.getText());
        String name = customerNameText.getText();
        String phone = phoneNumberText.getText();
        String address = addressText.getText();
        String postal = postalCodeText.getText();
        FirstLevelDivisions divisions = divisionComboBox.getSelectionModel().getSelectedItem();
        int divisionId = divisions.getDivisionId();

        // create a customer object with the collected data
        Customers newCustomer = new Customers(id, name, address, postal, phone, divisionId);

        // call create method to insert into the database
        CustomerDAO dao = new CustomerDAO();
        dao.update(newCustomer);

        displayNewScreen(updateCustomerButton, "/view/Customers.fxml");
    }

    private static Customers item = null;

    public static void holdData(Customers selectedCustomer) {
        item = selectedCustomer;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set combo box with values
        countryComboBox.setItems(new CountriesDAO().read());

        // set the field values with the data passed in
        customerIdText.setText(String.valueOf(item.getCustomerId()));
        customerNameText.setText(item.getCustomerName());
        phoneNumberText.setText(item.getPhone());
        addressText.setText(item.getAddress());
        postalCodeText.setText(item.getPostalCode());
        countryComboBox.getSelectionModel().select(item.getCountryObject());
        divisionComboBox.getSelectionModel().select(item.getDivisionsObject());

    }
}

