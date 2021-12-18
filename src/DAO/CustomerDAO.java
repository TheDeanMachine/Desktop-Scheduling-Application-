package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO implements DataAccessObject<Customers> {
    ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create() {

        //TODO
        // crete query to insert data

    }

    @Override
    public ObservableList<Customers> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT customer_id, customer_name, address, postal_code, phone, division_id FROM customers;";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String address = resultSet.getString("address");
                String code = resultSet.getString("postal_code");
                String phone = resultSet.getString("phone");
                int divisionId = resultSet.getInt("division_id");

                Customers customer = new Customers(customerId, name, address, code, phone, divisionId);
                listOfCustomers.add(customer);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfCustomers;
    }

    public ObservableList<Customers> getCustomerContactInformation(int id) {
        try {
            query = "SELECT c.customer_id, c.customer_name, c.address, c.postal_code, c.phone, c.division_Id \n" +
                    "FROM customers c \n" +
                    "JOIN appointments a ON a.customer_id = c.customer_id \n" +
                    "WHERE a.contact_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String address = resultSet.getString("address");
                String code = resultSet.getString("postal_code");
                String phone = resultSet.getString("phone");
                int divisionId = resultSet.getInt("division_id");

                Customers customer = new Customers(customerId, name, address, code, phone, divisionId);
                listOfCustomers.add(customer);
            }
        } catch (SQLException e) {
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
