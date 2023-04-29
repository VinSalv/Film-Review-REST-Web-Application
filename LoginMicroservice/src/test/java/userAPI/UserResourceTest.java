package userAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userAPI.microservices.beans.User;
import userAPI.microservices.beans.UserCookie;
import userAPI.microservices.resources.UserResource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserResourceTest {
    private UserResource service;

    @BeforeEach
    protected void setUp() {
        service = new UserResource();
    }

    @Test
    public final void testAddUser() throws SQLException {
        User user_to_add = new User();
        user_to_add.setUsername("prova");
        user_to_add.setEmail("prova@gmail.com");
        user_to_add.setPassword("prova");
        user_to_add.setRole("client");
        user_to_add.setBan(0);
        Assertions.assertTrue(service.addUser(user_to_add));
    }

    @Test
    public final void testEddUser() throws SQLException {
        User user_to_add = new User();
        user_to_add.setId_user(11);
        user_to_add.setUsername("provamodificata");
        user_to_add.setEmail("provamodificata@gmail.com");
        user_to_add.setPassword("provamodificata");
        user_to_add.setRole("client");
        user_to_add.setBan(0);
        Assertions.assertTrue(service.editUser(user_to_add));
    }

    @Test
    public final void testDeleteUser() throws SQLException {
        Assertions.assertTrue(service.deleteUser(11));
    }

    @Test
    public final void testGetUser() throws JsonProcessingException, SQLException {
        User expected = new User();
        expected.setId_user(2);
        expected.setUsername("vincenzo");
        expected.setEmail("vincenzo@gmail.com");
        expected.setPassword("vincenzo");
        expected.setRole("manager");
        expected.setBan(0);
        User result = service.getUser(2);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testGetNoBannedUsers() throws JsonProcessingException, SQLException {
        List<UserCookie> expected = new ArrayList<>();
        expected.add(new UserCookie(2, "giuseppe", "manager", 0));
        expected.add(new UserCookie(3, "vincenzo", "manager", 0));
        expected.add(new UserCookie(4, "paolo", "manager", 0));
        expected.add(new UserCookie(5, "dario", "manager", 0));
        expected.add(new UserCookie(11, "rita", "client", 0));
        List<UserCookie> result = service.getNoBannedUsers();
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @Test
    public final void testUsers() throws JsonProcessingException, SQLException {
        List<UserCookie> expected = new ArrayList<>();
        expected.add(new UserCookie(2, "giuseppe", "manager", 0));
        expected.add(new UserCookie(3, "vincenzo", "manager", 0));
        expected.add(new UserCookie(4, "paolo", "manager", 0));
        expected.add(new UserCookie(5, "dario", "manager", 0));
        expected.add(new UserCookie(6, "nicola", "client", 1));
        expected.add(new UserCookie(7, "saverio", "client", 1));
        expected.add(new UserCookie(8, "francesco", "client", 1));
        expected.add(new UserCookie(9, "raffaele", "client", 1));
        expected.add(new UserCookie(10, "michele", "client", 1));
        expected.add(new UserCookie(11, "rita", "client", 0));
        List<UserCookie> result = service.getUsers();
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }

    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
