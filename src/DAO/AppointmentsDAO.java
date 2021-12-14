package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;


import java.sql.*;
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
            query = "SELECT app.appointment_id, app.title, app.description, app.location, con.contact_name, app.type,\n" +
                    "app.start, app.end, app.customer_id, app.user_id \n" +
                    "FROM appointments app \n" +
                    "JOIN contacts con ON con.contact_id = app.contact_id;" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                String location = resultSet.getString(4);
                String contact = resultSet.getString(5);
                String type = resultSet.getString(6);

                Timestamp resultSetTimestamp = resultSet.getTimestamp(7);
                LocalDateTime start = resultSetTimestamp.toLocalDateTime();

                Timestamp resultSetTimestamp1 = resultSet.getTimestamp(8);
                LocalDateTime end = resultSetTimestamp1.toLocalDateTime();

                int customerId = resultSet.getInt(9);
                int userId = resultSet.getInt(10);


                Appointments appointment =
                        new Appointments(id, title, description, location, contact, type, start, end, customerId, userId);
                listOfAppointments.add(appointment);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    public ObservableList<Appointments> findByContactId(int id) {
        try {
            query = "SELECT appointment_id, title, description, type, start, end, customer_id \n" +
                    "FROM appointments WHERE contact_id = ?;" ;

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            preparedStatement.execute();


            while(resultSet.next()){
                int appId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                String type = resultSet.getString(4);

                Timestamp resultSetTimestamp = resultSet.getTimestamp(5);
                LocalDateTime start = resultSetTimestamp.toLocalDateTime();

                Timestamp resultSetTimestamp1 = resultSet.getTimestamp(6);
                LocalDateTime end = resultSetTimestamp1.toLocalDateTime();

                int customerId = resultSet.getInt(7);

                Appointments appointment = new Appointments(appId, title, description, type, start, end, customerId);
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
