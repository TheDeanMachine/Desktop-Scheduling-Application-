package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO implements DataAccessObject<Customers> {
    ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

//    query = "SELECT c.customer_ID, c.customer_name, c.address, c.postal_code, c.phone, fld.division, con.country \n" +
//            "FROM customers c \n" +
//            "JOIN first_level_divisions fld ON c.division_Id = fld.division_Id \n" +
//            "JOIN countries con ON fld.country_Id = con.country_Id;";

    @Override
    public void create() {

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

                // TODO
                //get and set division name and country

                String division = customer.getDivision();
                customer.setDivision(division);

                String country ; ///??

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
