package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to implement CRUD operations on the Customers table.
 */
public class CustomerDAO implements DataAccessObject<Customers> {
    ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    String query;

    /**
     * This method is used to insert a customer object/record into the database.
     * @param object the customer object to be inserted into the database.
     */
    @Override
    public void create(Customers object) {
        try {
            query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) \n" +
                    "VALUES (?, ?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(query);
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

    /**
     * This method is used to access and read all the customers.
     * @return a list of customers from the customers table.
     */
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

    /**
     * This method is used to get contact information based on associated contact.
     * @param id the contact id.
     * @return list of contact information for a set of customers.
     */
    public ObservableList<Customers> getCustomerContactInformation(int id) { // not used anymore
        try {
            query = "SELECT distinct c.customer_id, c.customer_name, c.address, c.postal_code, c.phone, c.division_Id \n" +
                    "FROM customers c \n" +
                    "JOIN appointments a ON a.customer_id = c.customer_id \n" +
                    "WHERE a.contact_id = ?;";

            preparedStatement = connection.prepareStatement(query);
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

    /**
     * This method is used to get a customers' entry/row from the database as an object, based on its id.
     * @param id the customers' id to search for.
     * @return a customers object, based on the parameter passed in.
     */
    public static Customers getCustomerObjectById(int id) {
        Customers customer = null;
        try {
            String query = "SELECT * FROM customers  \n" +
                            "WHERE customer_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String address = resultSet.getString("address");
                String code = resultSet.getString("postal_code");
                String phone = resultSet.getString("phone");
                int divisionId = resultSet.getInt("division_id");

                customer = new Customers(customerId, name, address, code, phone, divisionId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * This method is used to update information for a customer object/record.
     * @param object the customer object to be updated.
     */
    @Override
    public void update(Customers object) {
        try {
            query = "UPDATE customers \n" +
                    "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? \n" +
                    "WHERE Customer_ID = ?;";

            preparedStatement = connection.prepareStatement(query);
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

    /**
     * Used to delete a record from the database.
     * @param id the customer id to be deleted.
     */
    @Override
    public void delete(int id) {
        try {
            query = "DELETE FROM customers \n" +
                    "WHERE Customer_ID = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
