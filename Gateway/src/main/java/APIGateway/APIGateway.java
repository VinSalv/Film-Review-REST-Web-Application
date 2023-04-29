package APIGateway;

import APIGateway.models.DataAggregation.FeedbackUsername;
import APIGateway.models.DataAggregation.FilmDetailsRecord;
import APIGateway.models.FilmApplication.Actor;
import APIGateway.models.FilmApplication.Feedback;
import APIGateway.models.FilmApplication.Film;
import APIGateway.models.FilmApplication.ReviewPageFilm;
import APIGateway.models.LoginApplication.User;
import APIGateway.models.LoginApplication.UserCookie;
import APIGateway.models.LoginApplication.UserLogin;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import static APIGateway.Util.*;

/**
 * Servlet class that function as an API Gateway.
 * It is the unique access point to the Application. Once it receives a request from the client,
 * it performs a request to a RESTful web service and then forwards the response to the client.
 * It also filters the incoming data and performs the data aggregation from different services , if needed
 */
@WebServlet(name = "APIGateway", value = {
        "/add-actor", "/edit-actor", "/delete-actor", "/get-actor", "/get-actors",
        "/add-feedback", "/edit-feedback", "/delete-feedback", "/get-feedback", "/get-feedback-by-user", "/get-feedback-by-film",
        "/add-film", "/edit-film", "/delete-film", "/get-film",
        "/add-user", "/edit-user", "/login-user", "/delete-user", "/get-user",
        "/toggle-ban-user", "/toggle-role-user", "/get-users",
        "/get-films-home-page", "/get-films-home-page-per-genre", "/get-film-review-page"
})
@MultipartConfig(maxFileSize = 16177215)
public class APIGateway extends HttpServlet {

    private final String actorMicroservice;
    private final String filmMicroservice;
    private final String feedbackMicroservice;
    private final String filmDiscovery;
    private final String userMicroservice;
    private final String loginMicroservice;

    public APIGateway() {
        String LOGIN_APPLICATION_ENDPOINT = null;
        String FILM_APPLICATION_ENDPOINT = null;

        try {
            InputStream input = new FileInputStream("./settings.properties");

            Properties prop = new Properties();
            prop.load(input);

            LOGIN_APPLICATION_ENDPOINT = prop.getProperty("login_endpoint") + "/api-login";
            FILM_APPLICATION_ENDPOINT = prop.getProperty("film_endpoint") + "/api";

        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        actorMicroservice = FILM_APPLICATION_ENDPOINT + "/actors";
        filmMicroservice = FILM_APPLICATION_ENDPOINT + "/films";
        feedbackMicroservice = FILM_APPLICATION_ENDPOINT + "/feedbacks";
        filmDiscovery = FILM_APPLICATION_ENDPOINT + "/query";

        loginMicroservice = LOGIN_APPLICATION_ENDPOINT + "/login";
        userMicroservice = LOGIN_APPLICATION_ENDPOINT + "/users";
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String path = Util.getRequestPath(request);
        PrintWriter pw = response.getWriter();
        try {
            switch (path) {
                // Actor
                case "/add-actor" -> response.setStatus(post(actorMicroservice, new Actor(request, true)));
                case "/edit-actor" -> response.setStatus(put(actorMicroservice, new Actor(request, false)));
                // Film
                case "/add-film" -> response.setStatus(post(filmMicroservice, new Film(request, true)));
                case "/edit-film" -> response.setStatus(put(filmMicroservice, new Film(request, false)));
                // Feedback
                case "/add-feedback" -> response.setStatus(post(feedbackMicroservice, new Feedback(request)));
                case "/edit-feedback" -> response.setStatus(put(feedbackMicroservice, new Feedback(request)));
                // User
                case "/add-user" -> response.setStatus(post(userMicroservice, new User(request, true)));
                case "/edit-user" -> response.setStatus(put(userMicroservice, new User(request, false)));
                case "/toggle-ban-user" -> response.setStatus(put(userMicroservice + "/ban" + "/" + Integer.parseInt(request.getParameter("ban")),
                        Integer.parseInt(request.getParameter("id_user"))));
                case "/toggle-role-user" -> response.setStatus(put(userMicroservice + "/role" + "/" + request.getParameter("role"),
                        Integer.parseInt(request.getParameter("id_user"))));

                // Login
                case "/login-user" -> pw.write(toJSON(put(loginMicroservice, new UserLogin(request), UserCookie.class)));

            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.flushBuffer();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        String path = Util.getRequestPath(req);
        PrintWriter pw = resp.getWriter();
        switch (path) {
            // Actors
            case "/get-actor" -> pw.write(toJSON(get(actorMicroservice + "/" + req.getParameter("id"), Actor.class)));
            case "/get-actors" -> pw.write(toJSON(get(actorMicroservice, null)));
            case "/delete-actor" -> resp.setStatus(delete(actorMicroservice + "/" + req.getParameter("id")));
            // Film
            case "/delete-film" -> resp.setStatus(delete(filmMicroservice + "/" + req.getParameter("id")));
            case "/get-film" -> pw.write(toJSON(get(filmMicroservice + "/" + req.getParameter("id"), Film.class)));
            case "/get-films" -> pw.write(toJSON(get(filmMicroservice, null)));
            // Feedback
            case "/delete-feedback" -> resp.setStatus(delete(feedbackMicroservice + "/" + req.getParameter("id_film") + "/" + req.getParameter("id_user")));
            case "/get-feedback" -> pw.write(toJSON(get(feedbackMicroservice + "/" + req.getParameter("id_film") + "/" + req.getParameter("id_user"), Feedback.class)));
            case "/get-feedback-by-film" -> pw.write(toJSON(get(feedbackMicroservice + "/film" + "/" + req.getParameter("id_film"), null)));
            case "/get-feedback-by-user" -> pw.write(toJSON(get(feedbackMicroservice + "/user" + "/" + req.getParameter("id_user"), null)));
            // Film Query
            case "/get-films-home-page" -> pw.write(toJSON(get(filmDiscovery + "/home-page", null)));
            case "/get-films-home-page-per-genre" -> pw.write(toJSON(get(filmDiscovery + "/home-page" + "/" + req.getParameter("genre"), null)));
            case "/get-film-review-page" -> {
                ReviewPageFilm film = (ReviewPageFilm) get(filmDiscovery + "/review-page" + "/" + req.getParameter("id"), ReviewPageFilm.class);
                @SuppressWarnings("unchecked")
                List<LinkedHashMap<String, String>> users = (List<LinkedHashMap<String, String>>) get(userMicroservice + "/no-banned", null);
                List<FeedbackUsername> feedbackUsernameList = new ArrayList<>();
                for (Feedback feedback : film.getFeedbackList()) {
                    feedbackUsernameList.add(new FeedbackUsername(feedback, users));
                }
                pw.write(toJSON(new FilmDetailsRecord(film, feedbackUsernameList)));
            }
            // User
            case "/delete-user" -> resp.setStatus(delete(userMicroservice + "/" + req.getParameter("id_user")));
            case "/get-user" -> pw.write(toJSON(get(userMicroservice + "/" + req.getParameter("id_user"), User.class)));
            case "/get-users" -> pw.write(toJSON(get(userMicroservice, null)));

        }
        pw.flush();
    }
}
