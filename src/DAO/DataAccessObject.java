package DAO;

public interface DataAccessObject {

    /// CRUD operations
    public abstract void create();
    public abstract int findById();
    public abstract void update();
    public abstract void delete();

}
