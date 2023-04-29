package APIGateway.models.FilmApplication;

import APIGateway.Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Class representing the Actor
 */
public class Actor {
    private int id;
    private String name;
    private String surname;

    public Actor(HttpServletRequest req, boolean add) throws Exception {
        if (!add) this.id = Integer.parseInt(req.getParameter("id"));
        this.name = Util.validate(req.getParameter("name"));
        this.surname = Util.validate(req.getParameter("surname"));
    }

    public Actor(String id) {
        this.id = Integer.parseInt(id);
    }

    public Actor() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}