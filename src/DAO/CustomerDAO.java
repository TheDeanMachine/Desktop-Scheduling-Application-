package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import utilities.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO implements DataAccessObject {



    @Override
    public void create() {

    }

    @Override
    public ObservableList read() {
        ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();

        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM customers";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String code = resultSet.getString(4);
                String phone = resultSet.getString(5);

                Customers customer = new Customers(id, name, address, code, phone);
                listOfCustomers.add(customer);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return listOfCustomers;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
