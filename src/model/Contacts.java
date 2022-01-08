package model;

/**
 * This class is used as the model for the representation of the contacts table.
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private String email;

    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    // Getters
    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Used to set what is displayed in the combo boxes of Contacts.
     * @return string value of the contact name.
     */
    @Override
    public String toString() {
        return contactName;
    }
}
