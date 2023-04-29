package userAPI.interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDb implements DbInterface {
    private static final String url = "jdbc:mysql://localhost:3306";
    private static final String extra = "/user_db?useTimezone=true&serverTimezone=UTC";
    private static final String userDb = "root";
    private static final String password = "root";
    private Connection connection;
    private boolean connected;


    /**
     * The object represents a particular Postgresql database connection, which will be created based on the specified
     * parameters.
     */
    public MySQLDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
        this.connected = false;
    }

    /**
     * This method connects the current object to a database, making them usable.
     */
    @Override
    public void connect() {
        if (!connected) {
            try {
                connection = DriverManager.getConnection(url + extra, userDb, password);
            } catch (SQLException e) {
                System.exit(99);
                return;
            }
            connected = true;
        }
    }

    /**
     * This method disconnects the current object to a database, making them unusable until that is reconnected.
     */
    @Override
    public void disconnect() {
        if (connected) {
            try {
                connection.close();
            } catch (SQLException e) {
                return;
            }
            connected = false;
        }
    }

    /**
     * This method indicates whether the current object is already connected to the database.
     *
     * @return true if is connected, else false.
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    /**
     * Getter method for the connection object.
     *
     * @return the SQLConnection object representing the connection to the database.
     */
    @Override
    public Connection getConnection() {
        return connection;
    }
}
