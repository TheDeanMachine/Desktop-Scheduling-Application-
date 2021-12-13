package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactsDAO implements DataAccessObject<Contacts> {
    ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;


    @Override
    public void create() {

    }

    @Override
    public ObservableList<Contacts> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT * FROM contacts";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);

                Contacts contact = new Contacts(id, name, email);
                listOfContacts.add(contact);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfContacts;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
