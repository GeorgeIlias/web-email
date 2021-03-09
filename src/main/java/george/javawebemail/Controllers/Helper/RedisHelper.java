package george.javawebemail.Controllers.Helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import george.javawebemail.Entities.User;
import george.javawebemail.Utilities.ObjectToString;

/**
 * Class to keep the code that would otherwise be duplicated to get the user
 * 
 * @author gIlias
 */
@Component
@SessionScope
public class RedisHelper {

    @Autowired
    public ValueOperations<String, String> lo;

    public void setUser(User user) {
        if (user != null) {
            lo.set("user", ObjectToString.getUserStringForStorage(user));
        } else {
            lo.set("user", null);
        }

    }

    public User getUser() {
        User returningUser = null;
        try {
            returningUser = new ObjectMapper()
                    .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(new Gson().toJson(lo.get("user")), User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return returningUser;
    }

}
