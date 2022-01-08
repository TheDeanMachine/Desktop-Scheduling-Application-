package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to implement CRUD operations on the Contacts table.
 */
public class ContactsDAO implements DataAccessObject<Contacts> {
    ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create(Contacts object) {

    }

    /**
     * This method is used to access and read all the contacts.
     * @return a list of contacts from the contacts table.
     */
    @Override
    public ObservableList<Contacts> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT contact_id, contact_name, email  FROM contacts;";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("contact_id");
                String name = resultSet.getString("contact_name");
                String email = resultSet.getString("email");

                Contacts contact = new Contacts(id, name, email);
                listOfContacts.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfContacts;
    }

    /**
     * This method is used to get the name of the contact based on its id.
     * @param id the contact id to search for.
     * @return the contact name based on the parameter passed in.
     */
    public static String getContactsNameById(int id) {
        String contactName = "";
        try {
            String query = "SELECT * FROM contacts WHERE contact_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                contactName = resultSet.getString("contact_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  contactName;
    }

    /**
     * This method is used to get a contacts' entry/row from the database as an object, based on its id.
     * @param id the contacts' id to search for.
     * @return a contact object, based on the parameter passed in.
     */
    public static Contacts getContactObjectById(int id){
        Contacts contact = null;
        try {
            String query = "SELECT * FROM contacts WHERE contact_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int contactId = resultSet.getInt("contact_id");
                String name = resultSet.getString("contact_name");
                String email = resultSet.getString("email");

                contact = new Contacts(contactId, name, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }


    @Override
    public void update(Contacts object) {

    }

    @Override
    public void delete(int id) {

    }
}
