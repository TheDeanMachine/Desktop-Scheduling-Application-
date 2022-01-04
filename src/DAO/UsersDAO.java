package DAO;

import controller.LogInScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersDAO implements DataAccessObject<Users> {
    ObservableList<Users> listOfUsers = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create(Users object) {

    }

    @Override
    public ObservableList<Users> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT user_id, user_name, password  FROM users";
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int userID = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String password = resultSet.getString("password");

                Users user = new Users(userID, userName, password);
                listOfUsers.add(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    public static Users getUserById(int id){
        Users user = null;
        try {
            String query = "SELECT * FROM users \n" +
                    "WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("User_ID");
                String name = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");

                user = new Users(userId, name, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static boolean checkForUser(String user, String password){
        int userId = 0;
        String name = "";
        String pass = "";
        String query;
        try {
            query = "SELECT * FROM users\n" +
                    "WHERE User_Name = ? AND Password = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userId = resultSet.getInt("User_ID");
                name = resultSet.getString("User_Name");
                pass = resultSet.getString("Password");
            }

            if(user.equals(name) && password.equals(pass)){
                LogInScreen.holdId(userId);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(Users object) {

    }

    @Override
    public void delete(int id) {

    }







}
