package APIGateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Utility class
 */
public class Util {

    /**
     * Method to get an HTTP request from other server
     *
     * @param request: received request
     * @return String content of request
     */
    public static String getRequestPath(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }

    /**
     * Method to extract trailer's field from an entire url
     *
     * @param trailer: string of url
     * @return trailer's field String
     */
    public static String extractTrailerString(String trailer) {
        String[] splitted = trailer.split("/");
        splitted = splitted[splitted.length - 1].split("=");
        splitted = splitted[1].split("&");
        return splitted[0];
    }

    /**
     * Method to validate a String field in order to avoid inadmissible symbols
     *
     * @param field: field to be checked
     * @return if the validation succeed return the String field, otherwise throws the Exception
     * @throws Exception: any kind of Exception
     */
    public static String validate(String field) throws Exception {
        field = field.trim();
        if (field.matches(".*<+.*>+.*") || field.matches("^$")) throw new Exception();
        return field;
    }

    /**
     * Method to validate a String email in order to avoid inadmissible symbols and to check regular expression
     *
     * @param email: email to be checked
     * @return if the validation succeed return the String email, otherwise throws the Exception
     * @throws Exception: any kind of Exception
     */
    public static String validate_email(String email) throws Exception {

        email = validate(email);
        if (!email.matches("[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}")) throw new Exception();
        return email;
    }


    /**
     * Method to convert an object to a JSON String
     *
     * @param object: object to be converted
     * @return JSON String of the object
     * @throws JsonProcessingException: formatted JSON exception
     */
    public static String toJSON(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    /**
     * Constructs a PUT request to the given url, passing the specified object as body
     *
     * @param url the url of the endpoint
     * @param obj the object to send
     * @return the status code associated to the response
     */
    public static int put(String url, Object obj) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity(obj, MediaType.APPLICATION_JSON));
        return response.getStatus();
    }

    /**
     * Constructs a PUT request to the given url, passing the specified object as body.
     * It expects in the response body an object of the passed class
     *
     * @param url              the url of the endpoint
     * @param obj              the object to send
     * @param responseObjClass the class of the object expected inside the response
     * @return the object extracted from the response body
     */
    public static Object put(String url, Object obj, Class<?> responseObjClass) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.entity(obj, MediaType.APPLICATION_JSON));
        return response.readEntity(responseObjClass);
    }


    /**
     * Constructs a POST request to the given url, passing the specified object as body
     *
     * @param url the url of the endpoint
     * @param obj the object to send
     * @return the status code associated to the response
     */
    public static int post(String url, Object obj) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(obj, MediaType.APPLICATION_JSON));
        return response.getStatus();
    }

    /**
     * Constructs a GET request to the given url
     *
     * @param url      the url of the endpoint
     * @param objClass the class of the expected object. Set to null if a collection is expected
     * @return the object extracted from the response body
     */
    public static Object get(String url, Class<?> objClass) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (objClass == null) return response.readEntity(new GenericType<>() {
        });
        else {
            return response.readEntity(objClass);
        }
    }

    /**
     * Constructs a DELETE request to the given url
     *
     * @param url the url of the endpoint
     * @return the status code associated to the response
     */
    public static int delete(String url) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.delete();
        return response.getStatus();
    }
}
