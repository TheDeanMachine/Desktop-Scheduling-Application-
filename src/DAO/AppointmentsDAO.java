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
            query = "SELECT appointment_id, title, description, location, type, start, end, " +
                    "customer_id, user_id, contact_id " +
                    "FROM appointments;" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("appointment_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String type = resultSet.getString("type");
                Timestamp resultSetTimestamp = resultSet.getTimestamp("start");
                LocalDateTime start = resultSetTimestamp.toLocalDateTime();
                Timestamp resultSetTimestamp1 = resultSet.getTimestamp("end");
                LocalDateTime end = resultSetTimestamp1.toLocalDateTime();
                int customerId = resultSet.getInt("customer_id");
                int userId = resultSet.getInt("user_id");
                int contactId = resultSet.getInt("contact_id");

                Appointments appointment =
                        new Appointments(id, title, description, location, type, start, end, customerId, userId, contactId);

                String contact = appointment.getContact(); //???
                appointment.setContact(contact);  //???

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

            resultSet = preparedStatement.executeQuery();



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
