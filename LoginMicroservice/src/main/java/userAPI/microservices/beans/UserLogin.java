package userAPI.microservices.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean representing the data associated to the login
 */
@XmlRootElement(name = "UserLogin")
@XmlAccessorType(XmlAccessType.NONE)
public class UserLogin {
    @XmlAttribute
    private String user;
    @XmlAttribute
    private String password;

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
