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

/**
 * This class is used to modify the customer in the database.
 */
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

    /**
     * This method is used return to the customer screen, without making changes.
     * Uses the cancel button as the event trigger.
     */
    @FXML
    void onActionBackToMain(ActionEvent event) throws IOException {
        displayNewScreen(cancelButton, "/view/Customers.fxml");
    }

    /**
     * This method is used to filter divisions by country.
     * When the user selects a country from the country combo box the divisions' combo box is set with the
     * divisions of that country.
     */
    @FXML
    void onActionFilterDivisionsByCountry(ActionEvent event) {
        // if country is selected clear the division preset selection
        divisionComboBox.getSelectionModel().clearSelection();

        // get the selected country, and filter divisions by it
        Countries countrySelection = countryComboBox.getSelectionModel().getSelectedItem();
        int countryId = countrySelection.getCountryId();
        divisionComboBox.setItems(new FirstLevelDivisionsDAO().getDivisionsByCountryId(countryId));

    }

    /**
     * This method is used to collect information and update the customer.
     * The user entries are collected in text field and combo boxes, and then used to create customer object.
     * That object is then passed into the database and updated based on the customer id.
     * @throws IOException catches input output errors.
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);

        int id = Integer.parseInt(customerIdText.getText());

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

        // no null check required, once set in add customer, cannot be made null
        int divisionId = divisionComboBox.getSelectionModel().getSelectedItem().getDivisionId();

        // create a customer object with the collected data
        Customers newCustomer = new Customers(id, name, address, postal, phone, divisionId);

        // call update method to insert into the database
        CustomerDAO dao = new CustomerDAO();
        dao.update(newCustomer);

//        new CustomerDAO().update(newCustomer);

        displayNewScreen(updateCustomerButton, "/view/Customers.fxml");
    }

    // holds the customer object passed in by the modify button in customer screen
    private static Customers item = null;

    /**
     * Sets the item variable with the customer object passed in, by the modify button in CustomerScreen.
     * @param selectedCustomer the customer object passed in.
     */
    public static void holdData(Customers selectedCustomer) {
        item = selectedCustomer;
    }

    /**
     * Initialize Method.
     * This method is from the interface Initializable, and is overridden here.
     * The method is loaded(initialized) when this controller gets called by the display method in CustomerScreen.
     * It contains instructions to set Combo Boxes with the data that they will be working with.
     * In Addition to that, it sets the customer values based on the object passed in.
     */
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

        // set the division combo box with the list
        int countryId = item.getCountryObject().getCountryId();
        divisionComboBox.setItems(new FirstLevelDivisionsDAO().getDivisionsByCountryId(countryId));

        // set combo box with the item from the list
        divisionComboBox.getSelectionModel().select(item.getDivisionsObject());

    }
}

