package DAO;


import javafx.collections.ObservableList;

public interface DataAccessObject {

    /// CRUD operations
    public abstract void create();
    public abstract ObservableList read();
    public abstract void update();
    public abstract void delete();

}
