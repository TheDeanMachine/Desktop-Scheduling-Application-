package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstLevelDivisionsDAO implements DataAccessObject<FirstLevelDivisions> {
    ObservableList<FirstLevelDivisions> listOfDivisions = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create(FirstLevelDivisions object) {

    }

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


    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
