package userAPI.microservices.resources;

import userAPI.interfaces.DBConnection;
import userAPI.microservices.beans.User;
import userAPI.microservices.beans.UserCookie;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Resource class for managing the users
 */
@Path("/users")
public class UserResource extends DBConnection {
    public UserResource() {
        super();
    }

    /**
     * Insert a new User inside the database
     *
     * @param user - the User to be added
     * @return true if the insertion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean addUser(User user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("INSERT INTO user_db.user (username, email, password, role, ban) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getBan());
            statement.execute();

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    /**
     * Edit the User data inside the database
     *
     * @param user - the User to be edited
     * @return true if the edit is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean editUser(User user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE user_db.user SET username = ?, email = ?, password = ?, role = ?, ban = ? WHERE id_user = ?");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());
            statement.setInt(5, user.getBan());
            statement.setInt(6, user.getId_user());
            statement.execute();

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    /**
     * Delete the User associated to the given id
     *
     * @param id_user - the id of the user to delete
     * @return true if the deletion is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @DELETE
    @Path("/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean deleteUser(@PathParam("id_user") int id_user) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("DELETE FROM user_db.user WHERE id_user = ?");
            statement.setInt(1, id_user);
            statement.execute();

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    /**
     * Returns the User data associated to the passed id
     *
     * @param id_user - the id of the user to retrieve
     * @return the User object containing all the data of the user
     * @throws SQLException if the connection with the database fails
     */
    @GET
    @Path("/{id_user}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@PathParam("id_user") int id_user) throws SQLException {
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

            statement = connection.prepareStatement("SELECT * FROM user_db.user WHERE id_user = ?");
            statement.setInt(1, id_user);
            rs = statement.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id_user"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("role"), rs.getInt("ban"));
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

    /**
     * Retrieves the list of users.
     *
     * @return a list of UserCookie object containing the data representing the cookie for each user
     * @throws SQLException if the connection to the database fails
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserCookie> getUsers() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<UserCookie> userList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_user, username, role, ban FROM user_db.user WHERE role!='admin' ORDER BY id_user");
            rs = statement.executeQuery();
            while (rs.next()) {
                userList.add(new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"), rs.getInt("ban")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return userList;
    }

    /**
     * Changes the status of the user, from banned to unbanned and viceversa
     *
     * @param id_user       the id of the user target
     * @param currentStatus the current status
     * @return true if the toggle is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Path("/ban/{currentStatus}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean toggleBan(int id_user, @PathParam("currentStatus") int currentStatus) throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE user_db.user SET ban = ? WHERE id_user = ?");
            statement.setInt(1, currentStatus == 1 ? 0 : 1);
            statement.setInt(2, id_user);
            statement.execute();

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    /**
     * Changes the role of the user, from client to manager and viceversa
     *
     * @param id_user     the id of the user target
     * @param currentRole the current role of the user
     * @return true if the toggle is completed correctly, false otherwise
     * @throws SQLException if the connection with the database fails
     */
    @PUT
    @Path("/role/{currentRole}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public boolean toggleRole(int id_user, @PathParam("currentRole") String currentRole) throws SQLException {
        // Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("UPDATE user_db.user SET role = ? WHERE id_user = ?");
            statement.setString(1, currentRole.equals("client") ? "manager" : "client");
            statement.setInt(2, id_user);
            statement.execute();

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return false;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return true;
    }

    /**
     * Retrieves the list of Not banned users.
     *
     * @return a list of UserCookie object containing the data representing the cookie for each target user
     * @throws SQLException if the connection to the database fails
     */
    @GET
    @Path("/no-banned")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserCookie> getNoBannedUsers() throws SQLException {
        //Init params
        db.connect();
        Connection connection = db.getConnection();
        Savepoint savepoint = null;
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<UserCookie> userList = new ArrayList<>();

        //Execute queries
        try {
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();

            statement = connection.prepareStatement("SELECT id_user, username, role, ban FROM user_db.user WHERE ban=0 and role!='admin' ORDER BY id_user");
            rs = statement.executeQuery();
            while (rs.next()) {
                userList.add(new UserCookie(rs.getInt("id_user"), rs.getString("username"), rs.getString("role"), rs.getInt("ban")));
            }

        } catch (SQLException e) {
            connection.rollback(savepoint);
            return null;
        } finally {
            connection.commit();
            db.disconnect();
        }
        return userList;
    }


}
