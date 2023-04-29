package userAPI.interfaces;

public abstract class DBConnection {
    public final DbInterface db;

    /**
     * Initialization and connection to the DataBase
     */
    public DBConnection() {
        this.db = new MySQLDb();
    }
}
