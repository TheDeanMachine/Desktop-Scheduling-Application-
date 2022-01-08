package model;

import utilities.TimeHelper;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is used as the model for the representation of the users table.
 */
public class Users {
    private int userID; //PK
    private String userName;
    private String password;

    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;

    public Users(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    // Getters
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Used to set what is displayed in the combo boxes of Users.
     * @return string value of the userId.
     */
    @Override
    public String toString() {
        return String.valueOf(userID);
    }
}
