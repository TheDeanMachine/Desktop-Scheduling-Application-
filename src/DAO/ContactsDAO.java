package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactsDAO implements DataAccessObject<Contacts> {
    ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;


    @Override
    public void create(Contacts object) {

    }

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

    // get contact name corresponding to contact id in appointments table
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


    @Override
    public void update() {

    }

    @Override
    public void delete(int id) {

    }
}
