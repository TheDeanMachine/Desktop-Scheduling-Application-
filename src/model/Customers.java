package model;

import DAO.CountriesDAO;
import DAO.FirstLevelDivisionsDAO;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    public String getDivision() {
        return FirstLevelDivisionsDAO.getDivisionNameById(divisionId);
    }

    public String getCountry(){
        return CountriesDAO.getCountryNameById(divisionId);
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

}
