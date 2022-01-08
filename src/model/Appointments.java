package model;

import DAO.ContactsDAO;
import DAO.CustomerDAO;
import DAO.UsersDAO;
import utilities.TimeHelper;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is used as the model for the representation of the Appointments table.
 */
public class Appointments {
    private int appointmentId; //PK
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId; //FK
    private int userId; //FK
    private int contactId; //FK

    /**
     * Overloaded constructor, used to set the distinct type of appointments for the reports page.
     * @param type the appointment type.
     */
    public Appointments(String type) {
        this.type = type;
    }

    // full constructor
    public Appointments(int appointmentId, String title, String description, String location, String type,
                        LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public int getContactId() {
        return contactId;
    }

    /**
     * Get the contact name, based on the contact id.
     * @return contact name as string.
     */
    public String getContact() {
        return ContactsDAO.getContactsNameById(contactId);
    }

    /**
     * Used to get customer information based on the customer id.
     * @return customer object.
     */
    public Customers getCustomerObject(){
        return CustomerDAO.getCustomerObjectById(customerId);
    }

    /**
     * Used to get user information based on the user id.
     * @return user object.
     */
    public Users getUserObject(){
        return UsersDAO.getUserById(userId);
    }

    /**
     * Used to get contact information based on the contact id.
     * @return contact object.
     */
    public Contacts getContactObject(){
        return ContactsDAO.getContactObjectById(contactId);
    }

    /**
     * Get the start appointment time as formatted string.
     * @return date time as formatted string.
     */
    public String getStartTimeAsString(){
        return TimeHelper.getFormattedDateTime(start);
    }

    /**
     * Get the end appointment time as formatted string.
     * @return date time as formatted string.
     */
    public String getEndTimeAsString(){
        return TimeHelper.getFormattedDateTime(end);
    }

    // Setters
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Used to set what is displayed in the combo boxes of Appointment.
     * @return string value of the appointment type.
     */
    @Override
    public String toString() {
        return type;
    }

}
