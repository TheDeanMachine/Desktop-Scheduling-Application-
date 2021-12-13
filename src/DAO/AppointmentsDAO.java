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
            query = "SELECT app.appointment_id, app.title, app.description, app.location, con.contact_name, app.type,\n" +
                    "app.start, app.end, app.customer_id, app.user_id\n" +
                    "FROM appointments app\n" +
                    "JOIN contacts con ON con.contact_id = app.contact_id;" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                String location = resultSet.getString(4);
                String contact = resultSet.getString(5);
                String type = resultSet.getString(6);
                LocalDateTime start = LocalDateTime.parse(resultSet.getString(7));
                LocalDateTime end = LocalDateTime.parse(resultSet.getString(8));
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

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }
}
