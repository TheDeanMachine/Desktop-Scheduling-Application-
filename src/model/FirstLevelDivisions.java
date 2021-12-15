package model;

import DAO.CountriesDAO;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FirstLevelDivisions {
    private int divisionId; //PK
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int countryId; //FK

    private String country; //?

    public FirstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    // Getters
    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountry(){
        return CountriesDAO.getCountryNameById(divisionId);
    }


    // Setters
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setDivision(String division) {
       this.division = division;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }


    public void setCountry(String country) {
        this.country = country;
    }
}
