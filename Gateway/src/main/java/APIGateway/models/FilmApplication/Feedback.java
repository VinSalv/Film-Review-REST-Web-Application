package APIGateway.models.FilmApplication;

import APIGateway.Util;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;

/**
 * Class representing the Feedback
 */
public class Feedback {
    private int id_user;
    private int id_film;
    private String comment;
    private Float score;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "it_IT", timezone = "Europe/Rome")
    private Date date = null;

    public Feedback(HttpServletRequest req) throws Exception {
        this.id_user = Integer.parseInt(req.getParameter("id_user"));
        this.id_film = Integer.parseInt(req.getParameter("id_film"));
        this.comment = Util.validate(req.getParameter("comment"));
        this.score = Float.parseFloat(req.getParameter("score"));
    }

    public Feedback() {

    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
