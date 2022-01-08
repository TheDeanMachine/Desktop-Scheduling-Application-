package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class is used to implement CRUD operations on the Appointments table.
 */
public class AppointmentsDAO implements DataAccessObject<Appointments> {
    ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet resultSet;
    String query;

    /**
     * This method is used to insert an appointment object/record into the database.
     * @param object the appointment object to be inserted into the database.
     */
    @Override
    public void create(Appointments object) {
        try {
            query = "INSERT INTO appointments (title, description, location, type, start, end, customer_id, user_id, " +
                    "contact_id) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getTitle());
            preparedStatement.setString(2, object.getDescription());
            preparedStatement.setString(3, object.getLocation());
            preparedStatement.setString(4, object.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(object.getStart()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(object.getEnd()));
            preparedStatement.setInt(7, object.getCustomerId());
            preparedStatement.setInt(8, object.getUserId());
            preparedStatement.setInt(9, object.getContactId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method is used to access and read all the appointments.
     * @return a list of appointments from the appointments table.
     */
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

    /**
     * This method is used to display a list of appointments based on a contact.
     * @param id the contact id used to generate a list of appointment.
     * @return list of appointments based on the parameter passed on in.
     */
    public ObservableList<Appointments> findAppointmentByContactId(int id) {
        try {
            query = "SELECT appointment_id, title, description, location, type, start, end, " +
                    "customer_id, user_id, contact_id " +
                    "FROM appointments WHERE contact_id = ?" ;

            preparedStatement = connection.prepareStatement(query);
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

    /**
     * Used to get the appointments of a given user.
     * @param id the user id to search for.
     * @return a list of appointments based on the parameter passed in.
     */
    public ObservableList<Appointments> findAppointmentByUserId(int id) {
        try {
            query = "SELECT appointment_id, title, description, location, type, start, end, " +
                    "customer_id, user_id, contact_id " +
                    "FROM appointments WHERE user_id = ? " +
                    "AND day(start) = DAY(current_date())";  // only get today's appointments

            preparedStatement = connection.prepareStatement(query);
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

    /**
     * Used to get the appointments of a given customer.
     * @param id the customer id to search for.
     * @return a list of appointments based on the parameter passed in.
     */
    public ObservableList<Appointments> findAppointmentByCustomerId(int id) {
        try {
            query = "SELECT appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id \n" +
                    "FROM appointments WHERE customer_id = ?;";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                int appid = resultSet.getInt("appointment_id");
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
                        new Appointments(appid, title, description, location, type, start, end, customerId, userId, contactId);
                listOfAppointments.add(appointment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    /**
     * Used to get the appointments of a given customer, excluding the current appointment.
     * @param id the customer id to search for.
     * @param app the appointment to exclude from the list.
     * @return a list of appointments based on the parameter passed in.
     */
    public ObservableList<Appointments> findAppointmentByCustomerId(int app, int id) {
        try {
            query = "SELECT appointment_id, title, description, location, type, start, end, customer_id, user_id, contact_id \n" +
                    "FROM appointments WHERE customer_id = ? \n" +
                    "AND NOT appointment_id = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, app);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
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
                        new Appointments(appId, title, description, location, type, start, end, customerId, userId, contactId);
                listOfAppointments.add(appointment);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return listOfAppointments;
    }

    /**
     * Used to get total number of appointments based on a customer.
     * @param id the customer id to search for.
     * @return the number of appointments based on the parameter passed in.
     */
    public int findTotalAppointmentByCustomer(int id) {
        int AppCount = 0;
        try {
            query = "SELECT COUNT(distinct appointment_id) as Appointment_Count \n" +
                    "FROM appointments \n" +
                    "WHERE Customer_ID = ?;";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                AppCount = resultSet.getInt("Appointment_Count");
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return AppCount;
    }


    /**
     * Used to get a list of appointment types for the reports' combo box.
     */
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

    /**
     * Generates a count for total number of appointments based on the search parameters.
     * @param month the month.
     * @param type the appointment type.
     * @return the number of appointments based on the search parameters.
     */
    public int getResultsForReports(int month, String type) {
        int count = 0;
        try {
            query = "SELECT * \n" +
                    "FROM appointments \n" +
                    "WHERE type = ? AND MONTH(start) = ? ;";

            preparedStatement = connection.prepareStatement(query);
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

    /**
     * Used to generate a list of appointments for this month.
     * @return list of appointments for this month.
     */
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

    /**
     * Used to generate a list of appointments for this week.
     * @return list of appointments for this week.
     */
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

    /**
     * This method is used to update information for the appointment object/record.
     * @param object the appointment object to be updated.
     */
    @Override
    public void update(Appointments object) {
        try {
            query = "UPDATE appointments \n" +
                    "SET title = ? , description = ?, location = ?, type = ?, start = ?, end = ?, customer_id = ?, " +
                    "user_id = ?, contact_id = ? \n" +
                    "WHERE Appointment_ID = ?;";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, object.getTitle());
            preparedStatement.setString(2, object.getDescription());
            preparedStatement.setString(3, object.getLocation());
            preparedStatement.setString(4, object.getType());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(object.getStart()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(object.getEnd()));
            preparedStatement.setInt(7, object.getCustomerId());
            preparedStatement.setInt(8, object.getUserId());
            preparedStatement.setInt(9, object.getContactId());
            preparedStatement.setInt(10, object.getAppointmentId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to delete a record from the database.
     * @param id the appointment id to be deleted.
     */
    @Override
    public void delete(int id) {
        try {
            query = "DELETE FROM appointments \n" +
                    "WHERE Appointment_ID = ?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to delete a record from the database based on a customer id.
     * @param id the customer id.
     */
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
