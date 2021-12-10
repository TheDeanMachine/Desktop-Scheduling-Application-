package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import utilities.JDBC;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDAO implements DataAccessObject {


    @Override
    public void create() {

    }

    @Override
    public ObservableList<Users> read() {
        ObservableList<Users> listOfUsers = FXCollections.observableArrayList();

        try {
            Connection connection = JDBC.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int userID = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                String password = resultSet.getString(3);

                Users user = new Users(userID, userName, password);
                listOfUsers.add(user);

            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return listOfUsers;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }







}
