package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to implement CRUD operations on the Countries table.
 */
public class CountriesDAO implements DataAccessObject<Countries> {
    ObservableList<Countries> listOfCountries = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create(Countries object) {

    }

    /**
     * This method is used to access and read all the countries.
     * @return a list of countries from the countries table.
     */
    @Override
    public ObservableList<Countries> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT country_id, country FROM countries";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("country_id");
                String countryName = resultSet.getString("country");

                Countries countries = new Countries(id, countryName);
                listOfCountries.add(countries);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfCountries;
    }


    /**
     * This method is used to get the name of the country based on its id.
     * @param id the country id to search for.
     * @return the country name based on the parameter passed in.
     */
    public static String getCountryNameById(int id) {
        String countryName = "";
        try {
            String query = "SELECT * FROM first_level_divisions fd \n" +
                    "JOIN countries con ON fd.Country_ID = con.Country_ID WHERE division_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                countryName = resultSet.getString("country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  countryName;
    }

    /**
     * This method is used to get a countries' entry/row from the database as an object, based on its id.
     * @param id the countries' id to search for.
     * @return a country object, based on the parameter passed in.
     */
    public static Countries getCountryObjectById(int id) {
        Countries country = null;
        try {
            String query = "SELECT * FROM first_level_divisions fd \n" +
                    "JOIN countries con ON fd.Country_ID = con.Country_ID WHERE division_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int countryID = resultSet.getInt("Country_ID");
                String countryName = resultSet.getString("Country");
                country = new Countries(countryID,countryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    @Override
    public void update(Countries object) {

    }

    @Override
    public void delete(int id) {

    }
}
