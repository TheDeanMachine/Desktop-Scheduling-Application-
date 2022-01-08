package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is used as the model for the representation of the contacts table.
 */
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

    /**
     * Used to set what is displayed in the combo boxes of Countries.
     * @return string value of the country.
     */
    @Override
    public String toString() {
        return country;
    }
}
