package userAPI.microservices.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean representing the data of the cookie
 */
@XmlRootElement(name = "UserCookie")
@XmlAccessorType(XmlAccessType.NONE)
public class UserCookie {
    @XmlAttribute
    private int id_user;
    @XmlAttribute
    private String username;
    @XmlAttribute
    private String role;
    @XmlAttribute
    private int ban;

    public UserCookie(int id_user, String username, String role, int ban) {
        this.id_user = id_user;
        this.username = username;
        this.role = role;
        this.ban = ban;
    }

    public UserCookie() {

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
