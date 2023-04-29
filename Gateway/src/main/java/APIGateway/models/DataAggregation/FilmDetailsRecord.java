package APIGateway.models.DataAggregation;

import APIGateway.models.FilmApplication.HomePageFilm;
import APIGateway.models.FilmApplication.ReviewPageFilm;

import java.util.List;

/**
 * Class representing all the data associated to a film. Used to perform the data aggregation
 */
public class FilmDetailsRecord extends HomePageFilm {
    private List<FeedbackUsername> feedbacks;

    public FilmDetailsRecord(ReviewPageFilm reviewPageFilm, List<FeedbackUsername> feedbacks) {
        super(reviewPageFilm.getId(), reviewPageFilm.getTitle(), reviewPageFilm.getGenre(), reviewPageFilm.getPlot(),
                reviewPageFilm.getTrailer(), reviewPageFilm.getPoster(), reviewPageFilm.getActorList(),
                reviewPageFilm.getAvgScore());
        this.feedbacks = feedbacks;
    }

    public List<FeedbackUsername> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackUsername> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
