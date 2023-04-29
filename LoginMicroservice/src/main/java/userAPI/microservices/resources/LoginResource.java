package userAPI.microservices.resources;

import userAPI.interfaces.DBConnection;
import userAPI.microservices.beans.UserCookie;
import userAPI.microservices.beans.UserLogin;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;

/**
 * Resource class for managing the login
 */
@Path("/login")
public class LoginResource extends DBConnection {
    public LoginResource() {
        super();
    }

    /**
     * Performs the login of the user.
     * It checks for the presence of the user inside the database.
     * If the data are correct, it returns the data representing the cookie
     *
     * @param user - the user data to check for
     * @return an UserCookie object containing the data representing the cookie
     * @throws SQLException if the connection to the database fails
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserCookie loginUser(UserLogin user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_user, username, role, ban FROM user_db.user WHERE (username = ? OR email = ?) AND BINARY(password) = ?");
            statement.setString(1, user.getUser());
            statement.setString(2, user.getUser());
            statement.setString(3, user.getPassword());
            rs = statement.executeQuery();
            if (rs.next()) {
                return new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"), rs.getInt("ban"));
            } else {
                return null;
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
    }


}
