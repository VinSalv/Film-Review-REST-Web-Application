package userAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

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
}
