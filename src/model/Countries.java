package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Countries {
    private int countryId; //PK
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    // Getters
    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country;
    }

    // Setters
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return country;
    }
}
