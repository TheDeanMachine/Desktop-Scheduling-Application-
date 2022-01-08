package model;

import DAO.CountriesDAO;
import DAO.FirstLevelDivisionsDAO;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is used as the model for the representation of the Customers table.
 */
public class Customers {
    private int customerId; //PK
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int divisionId; //FK

    public Customers(int customerId, String customerName, String address, String postalCode, String phone,
                     int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    // Getters
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Used to get the division name corresponding to the division id.
     * @return division name string.
     */
    public String getDivision() {
        return FirstLevelDivisionsDAO.getDivisionNameById(divisionId);
    }

    /**
     * Used to get the country based on its division identifier.
     * @return country name as a string.
     */
    public String getCountry(){
        return CountriesDAO.getCountryNameById(divisionId);
    }

    /**
     * Used to get the country based on its division identifier.
     * @return country object.
     */
    public Countries getCountryObject(){
        return CountriesDAO.getCountryObjectById(divisionId);
    }

    /**
     * Used to get division information based on the division id.
     * @return division object.
     */
    public FirstLevelDivisions getDivisionsObject(){
        return FirstLevelDivisionsDAO.getDivisionObjectById(divisionId);
    }

    // Setters
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Used to set what is displayed in the combo boxes of Customers.
     * @return string value of the customer id.
     */
    @Override
    public String toString() {
        return String.valueOf(customerId);
    }
}
