package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class is used to implement CRUD operations on the First Level Divisions table.
 */
public class FirstLevelDivisionsDAO implements DataAccessObject<FirstLevelDivisions> {
    ObservableList<FirstLevelDivisions> listOfDivisions = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create(FirstLevelDivisions object) {

    }

    /**
     * This method is used to access and read all the divisions.
     * @return a list of divisions from the first level divisions table.
     */
    @Override
    public ObservableList<FirstLevelDivisions> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT division_id, division, country_id FROM first_level_divisions";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("division_id");
                String divisionName = resultSet.getString("division");
                int countryId = resultSet.getInt("country_id");

                FirstLevelDivisions divisions = new FirstLevelDivisions(id, divisionName, countryId);
                listOfDivisions.add(divisions);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfDivisions;
    }

    /**
     * This method is used to get a list of divisions based on a country id.
     * @param id the country id.
     * @return a list of divisions based on a country.
     */
    public ObservableList<FirstLevelDivisions> getDivisionsByCountryId(int id) {
        try {
            query = " SELECT * FROM first_level_divisions \n" +
                    " WHERE country_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int divisionId = resultSet.getInt("division_id");
                String divisionName = resultSet.getString("division");
                int countryId = resultSet.getInt("country_id");

                FirstLevelDivisions divisions = new FirstLevelDivisions(divisionId, divisionName, countryId);
                listOfDivisions.add(divisions);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfDivisions;
    }


    /**
     * This method is used to get the name of the division based on its id.
     * @param id the division id to search for.
     * @return the division name based on the parameter passed in.
     */
    public static String getDivisionNameById(int id) {
        String divisionName = "";
        try {
            String query = "SELECT * FROM first_level_divisions WHERE division_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                divisionName = resultSet.getString("division");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  divisionName;
    }

    /**
     * This method is used to get a division entry/row from the database as an object, based on its id.
     * @param id the division id to search for.
     * @return a division object, based on the parameter passed in.
     */
    public static FirstLevelDivisions getDivisionObjectById(int id) {
        FirstLevelDivisions division = null;
        try {
            String query = "SELECT * FROM first_level_divisions WHERE division_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                int countryId = resultSet.getInt("Country_ID");
                division = new FirstLevelDivisions(divisionId, divisionName, countryId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  division;
    }

    @Override
    public void update(FirstLevelDivisions object) {

    }

    @Override
    public void delete(int id) {

    }
}
