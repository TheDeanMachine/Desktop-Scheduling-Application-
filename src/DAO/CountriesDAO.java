package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Customers;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountriesDAO implements DataAccessObject<Countries> {
    ObservableList<Countries> listOfCountries = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create() {

    }

    @Override
    public ObservableList<Countries> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT * FROM countries";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String countryName = resultSet.getString(2);

                Countries countries = new Countries(id, countryName);
                listOfCountries.add(countries);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfCountries;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
