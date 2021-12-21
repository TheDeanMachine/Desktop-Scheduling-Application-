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
    public void create(Customers object) {
        try {
            query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) \n" +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, object.getCustomerName());
            preparedStatement.setString(2, object.getAddress());
            preparedStatement.setString(3, object.getPostalCode());
            preparedStatement.setString(4, object.getPhone());
            preparedStatement.setInt(5, object.getDivisionId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void update(Customers object) {
        try {
            query = "UPDATE customers \n" +
                    "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? \n" +
                    "WHERE Customer_ID = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getCustomerName());
            preparedStatement.setString(2, object.getAddress());
            preparedStatement.setString(3, object.getPostalCode());
            preparedStatement.setString(4, object.getPhone());
            preparedStatement.setInt(5, object.getDivisionId());
            preparedStatement.setInt(6, object.getCustomerId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            query = "DELETE FROM customers \n" +
                    "WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
