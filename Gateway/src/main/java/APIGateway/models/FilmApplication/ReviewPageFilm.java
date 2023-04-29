package APIGateway.models.FilmApplication;

import java.util.List;

/**
 * Class Representing all data of the film together with the average score and the list of feedbacks
 */
public class ReviewPageFilm extends HomePageFilm {

    private List<Feedback> feedbackList;

    public ReviewPageFilm() {

    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
