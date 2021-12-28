package utilities;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC {
    private static final String jdbcUrl =   "jdbc:mysql://localhost/client_schedule" + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    public static Connection getConnection() {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }

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
