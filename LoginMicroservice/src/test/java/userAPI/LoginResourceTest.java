package userAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import userAPI.microservices.beans.UserCookie;
import userAPI.microservices.beans.UserLogin;
import userAPI.microservices.resources.LoginResource;

import java.sql.SQLException;

public class LoginResourceTest {
    private LoginResource service;

    @BeforeEach
    protected void setUp() {
        service = new LoginResource();
    }

    @Test
    public final void testLoginUser() throws JsonProcessingException, SQLException {
        UserLogin userLogin = new UserLogin();
        userLogin.setUser("vincenzo");
        //userLogin.setUser("vincenzo@gmail.com");
        userLogin.setPassword("vincenzo");
        UserCookie result = service.loginUser(userLogin);
        UserCookie expected = new UserCookie(2, "vincenzo", "manager", 0);
        Assertions.assertEquals(Util.toJSON(expected), Util.toJSON(result));
    }


    @AfterEach
    protected void tearDown() {
        service = null;
    }
}
