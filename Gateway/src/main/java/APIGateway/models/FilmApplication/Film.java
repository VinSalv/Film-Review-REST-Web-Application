package APIGateway.models.FilmApplication;

import APIGateway.EnumGenre;
import APIGateway.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Class representing the film
 */
public class Film {

    private int id;
    private String title;
    private String genre;
    private String plot;
    private String trailer;
    private String poster;
    private List<Actor> actorList;

    public Film() {

    }

    public Film(HttpServletRequest req, boolean add) throws Exception {
        if (!add) id = Integer.parseInt(req.getParameter("id"));
        title = Util.validate(req.getParameter("title"));
        plot = Util.validate(req.getParameter("plot"));
        genre = Util.validate(req.getParameter("genre"));
        trailer = Util.validate(req.getParameter("trailer"));
        poster = new String(Base64.getEncoder().encode(req.getPart("poster").getInputStream().readAllBytes()));
        List<Actor> list = new ArrayList<>();
        String[] ids = req.getParameterValues("actors");
        for (String id : ids) {
            list.add(new Actor(id));
        }
        actorList = list;
    }

    public Film(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster,
                List<Actor> actorList) {
        this.id = id;
        this.title = title;
        this.genre = genre.name();
        this.plot = plot;
        this.poster = new String(Base64.getEncoder().encode(poster));
        this.trailer = Util.extractTrailerString(trailer);
        this.actorList = actorList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public EnumGenre getGenre() {
        return EnumGenre.valueOf(genre);
    }

    public void setGenre(EnumGenre genre) {
        this.genre = genre.name();
    }

    public byte[] getPoster() {
        return Base64.getDecoder().decode(poster);
    }

    public void setPoster(byte[] poster) {
        this.poster = new String(Base64.getEncoder().encode(poster));
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }
}
