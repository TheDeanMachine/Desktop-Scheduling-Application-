package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class AppointmentsDAO implements DataAccessObject<Appointments> {
    ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;


    @Override
    public void create() {

    }

    @Override
    public ObservableList<Appointments> read() {
        try {
            statement = connection.createStatement();
            query = "SELECT * FROM appointments;" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                String location = resultSet.getString(4);
                String type = resultSet.getString(5);
                LocalDateTime start = LocalDateTime.parse(resultSet.getString(6));
                LocalDateTime end = LocalDateTime.parse(resultSet.getString(7));
                int customerId = resultSet.getInt(12);
                int userId = resultSet.getInt(13);


                Appointments appointment =
                        new Appointments(id, title, description, location, type, start, end, customerId, userId);
                listOfAppointments.add(appointment);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
