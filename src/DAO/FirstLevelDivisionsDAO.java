package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.FirstLevelDivisions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FirstLevelDivisionsDAO implements DataAccessObject<FirstLevelDivisions> {
    ObservableList<FirstLevelDivisions> listOfDivisions = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create() {

    }

    @Override
    public ObservableList<FirstLevelDivisions> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT * FROM first_level_divisions";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String divisionName = resultSet.getString(2);
                int countryId = resultSet.getInt(7);

                FirstLevelDivisions divisions = new FirstLevelDivisions(id, divisionName, countryId);
                listOfDivisions.add(divisions);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfDivisions;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
