package DAO;

import javafx.collections.ObservableList;
import utilities.JDBC;
import java.sql.Connection;

/**
 * This interface is used to create the Data Access Object pattern.
 * This interface implemented by several classes and is used to
 * separate low level data accessing from high level operations.
 * @param <T> The type safe object to be implemented.
 */
public interface DataAccessObject<T> {
    // any class that implements this interface will need a connection
    Connection connection = JDBC.getConnection();

    /// CRUD operations
    void create(T object);
    ObservableList<T> read();
    void update(T object);
    void delete(int id);

}
