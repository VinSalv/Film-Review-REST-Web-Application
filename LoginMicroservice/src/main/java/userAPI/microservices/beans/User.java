package userAPI.microservices.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean representing the User
 */
@XmlRootElement(name = "User")
@XmlAccessorType(XmlAccessType.NONE)
public class User {
    @XmlAttribute
    private int id_user;
    @XmlAttribute
    private String username;
    @XmlAttribute
    private String email;
    @XmlAttribute
    private String password;
    @XmlAttribute
    private String role;
    @XmlAttribute
    private int ban;

    public User(int id_user, String username, String email, String password, String role, int ban) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.ban = ban;
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
