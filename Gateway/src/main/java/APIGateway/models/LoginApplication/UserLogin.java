package APIGateway.models.LoginApplication;

import APIGateway.Util;

import javax.servlet.http.HttpServletRequest;

public class UserLogin {
    private String user;
    private String password;

    public UserLogin(HttpServletRequest req) throws Exception {
        user = Util.validate(req.getParameter("user"));
        password = Util.validate(req.getParameter("password"));
    }

    public UserLogin() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
