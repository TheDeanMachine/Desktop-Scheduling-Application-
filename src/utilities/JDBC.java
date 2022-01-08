package utilities;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class is used to establish a connection with the database.
 */
public abstract class JDBC {
    private static final String jdbcUrl =   "jdbc:mysql://localhost/client_schedule" + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    /**
     * Singleton pattern.
     * ask for the current connection, or creates one.
     * @return the connection.
     */
    public static Connection getConnection() {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }

    /**
     * Creates a connection to the database.
     * Uses the driver manager and field values to form a connection.
     * Connection is opened in Main, before launching the JavaFx application.
     */
    protected static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database.
     * Connection is closed after exiting from the JavaFx application.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
