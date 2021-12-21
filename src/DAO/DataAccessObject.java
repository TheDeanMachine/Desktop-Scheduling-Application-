package DAO;

import javafx.collections.ObservableList;
import utilities.JDBC;
import java.sql.Connection;

public interface DataAccessObject<T> {
    Connection connection = JDBC.getConnection();

    /// CRUD operations
    public abstract void create(T object);
    public abstract ObservableList<T> read();
    public abstract void update();
    public abstract void delete(int id);

}
