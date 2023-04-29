package APIGateway.models.FilmApplication;

import APIGateway.EnumGenre;

import java.util.List;

/**
 * Class Representing all data of the film together with the average score
 */
public class HomePageFilm extends Film {

    private float avgScore;

    public HomePageFilm(int id, String title, EnumGenre genre, String plot, String trailer, byte[] poster, List<Actor> actors, float avgScore) {
        super(id, title, genre, plot, trailer, poster, actors);
        this.avgScore = avgScore;
    }

    public HomePageFilm() {

    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }
}
