package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;


public class AppointmentsDAO implements DataAccessObject<Appointments> {
    ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
    Statement statement;
    ResultSet resultSet;
    String query;

    @Override
    public void create(Appointments object) {
        try {
            query = "INSERT INTO appointments (title, description, location, type, start, end, customer_id, user_id, " +
                    "contact_id) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getTitle());
            preparedStatement.setString(2, object.getDescription());
            preparedStatement.setString(3, object.getLocation());
            preparedStatement.setString(4, object.getType());
            preparedStatement.setString(5, String.valueOf(object.getStart()));
            preparedStatement.setString(6, String.valueOf(object.getEnd()));
            preparedStatement.setInt(7, object.getCustomerId());
            preparedStatement.setInt(8, object.getUserId());
            preparedStatement.setInt(9, object.getContactId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime();
                int customerId = resultSet.getInt("customer_id");
                int userId = resultSet.getInt("user_id");
                int contactId = resultSet.getInt("contact_id");

                Appointments appointment =
                        new Appointments(id,title,description,location,type,start,end,customerId,userId,contactId);
                listOfAppointments.add(appointment);
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    // displays the reports appointments by contact
    public ObservableList<Appointments> findAppointmentByContactId(int id) {
        try {
            query = "SELECT appointment_id, title, description, location, type, start, end, " +
                    "customer_id, user_id, contact_id " +
                    "FROM appointments WHERE contact_id = ?" ;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int appId = resultSet.getInt("appointment_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String type = resultSet.getString("type");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime();
                int customerId = resultSet.getInt("customer_id");
                int userId = resultSet.getInt("user_id");
                int contactId = resultSet.getInt("contact_id");

                Appointments appointment =
                        new Appointments(appId,title,description,location,type,start,end,customerId,userId,contactId);
                listOfAppointments.add(appointment);

            }

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    // gets list of types for reports combo box
    public ObservableList<Appointments> getListOfTypes(){
        try {
            statement = connection.createStatement();
            query = "SELECT distinct type FROM appointments;" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String type = resultSet.getString("type");

                Appointments appointment = new Appointments(type);
                listOfAppointments.add(appointment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }


    public int getResultsForReports(int month, String type) {
        int count = 0;
        try {
            query = "SELECT * \n" +
                    "FROM appointments \n" +
                    "WHERE type = ? AND MONTH(start) = ? ;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, month);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                int appId = resultSet.getInt("appointment_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String appType = resultSet.getString("type");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime();
                int customerId = resultSet.getInt("customer_id");
                int userId = resultSet.getInt("user_id");
                int contactId = resultSet.getInt("contact_id");

                Appointments appointment =
                        new Appointments(appId,title,description,location,appType,start,end,customerId,userId,contactId);
                listOfAppointments.add(appointment);
                count = listOfAppointments.size();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public ObservableList<Appointments> getThisMonthsAppointments() {
        try {
            statement = connection.createStatement();
            query = "SELECT * \n" +
                    "FROM appointments \n" +
                    "WHERE MONTH(start) = MONTH(CURRENT_DATE()) \n" +
                    "AND DATE_ADD(start, INTERVAL 1 MONTH);" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("appointment_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String type = resultSet.getString("type");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime();
                int customerId = resultSet.getInt("customer_id");
                int userId = resultSet.getInt("user_id");
                int contactId = resultSet.getInt("contact_id");

                Appointments appointment =
                        new Appointments(id,title,description,location,type,start,end,customerId,userId,contactId);
                listOfAppointments.add(appointment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    public ObservableList<Appointments> getThisWeeksAppointments() {
        try {
            statement = connection.createStatement();
            query = "SELECT *\n" +
                    "FROM appointments\n" +
                    "WHERE WEEK(start) = WEEK(CURRENT_DATE()) \n" +
                    "AND DATE_ADD(start, INTERVAL 1 WEEK);" ;
            resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                int id = resultSet.getInt("appointment_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String location = resultSet.getString("location");
                String type = resultSet.getString("type");
                LocalDateTime start = resultSet.getTimestamp("start").toLocalDateTime();
                LocalDateTime end = resultSet.getTimestamp("end").toLocalDateTime();
                int customerId = resultSet.getInt("customer_id");
                int userId = resultSet.getInt("user_id");
                int contactId = resultSet.getInt("contact_id");

                Appointments appointment =
                        new Appointments(id,title,description,location,type,start,end,customerId,userId,contactId);
                listOfAppointments.add(appointment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    @Override
    public void update(Appointments object) {
        try {
            query = "UPDATE appointments \n" +
                    "SET title = ? , description = ?, location = ?, type = ?, start = ?, end = ?, customer_id = ?, " +
                    "user_id = ?, contact_id = ? \n" +
                    "WHERE Appointment_ID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getTitle());
            preparedStatement.setString(2, object.getDescription());
            preparedStatement.setString(3, object.getLocation());
            preparedStatement.setString(4, object.getType());
            preparedStatement.setString(5, String.valueOf(object.getStart()));
            preparedStatement.setString(6, String.valueOf(object.getEnd()));
            preparedStatement.setInt(7, object.getCustomerId());
            preparedStatement.setInt(8, object.getUserId());
            preparedStatement.setInt(9, object.getContactId());
            preparedStatement.setInt(10, object.getAppointmentId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            query = "DELETE FROM appointments \n" +
                    "WHERE Appointment_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCustomerAppointments(int id) {
        try {
            String query = "DELETE FROM appointments \n" +
                    "WHERE Customer_ID = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
