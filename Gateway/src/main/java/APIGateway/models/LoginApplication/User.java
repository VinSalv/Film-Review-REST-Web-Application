package APIGateway.models.LoginApplication;

import APIGateway.Util;

import javax.servlet.http.HttpServletRequest;

public class User {
    private int id_user;
    private String username;
    private String email;
    private String password;
    private String role;
    private int ban;

    public User(HttpServletRequest req, boolean add) throws Exception {
        if (!add) {
            id_user = Integer.parseInt(req.getParameter("id_user"));
            role = Util.validate((req.getParameter("role")));
        } else {
            role = "client";
        }
        username = Util.validate(req.getParameter("username"));
        email = Util.validate_email(req.getParameter("email"));
        password = Util.validate(req.getParameter("password"));
    }

    public User() {

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getBan() {
        return ban;
    }

    public void setBan(int ban) {
        this.ban = ban;
    }
}
