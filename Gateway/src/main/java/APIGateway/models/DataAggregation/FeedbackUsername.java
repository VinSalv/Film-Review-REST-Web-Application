package APIGateway.models.DataAggregation;

import APIGateway.models.FilmApplication.Feedback;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class representing a Feedback containing the username of the reviewer. Used to perform data aggregation
 */
public class FeedbackUsername {
    private final String comment;
    private final float score;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "it_IT", timezone = "Europe/Rome")
    private final Date date;
    private final String username;

    public FeedbackUsername(Feedback feedback, List<LinkedHashMap<String, String>> users) {
        comment = feedback.getComment();
        score = feedback.getScore();
        date = feedback.getDate();

        for (Map user : users) {
            if ((int) user.get("id_user") == feedback.getId_user()) {
                username = (String) user.get("username");
                return;
            }
        }
        username = "Unregistered user";
    }

    public String getComment() {
        return comment;
    }

    public float getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }
}
