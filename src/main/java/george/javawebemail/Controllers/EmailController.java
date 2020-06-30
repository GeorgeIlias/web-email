package george.javawebemail.Controllers;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;
import george.javawebemail.Service.EmailService;
import george.javawebemail.Utilities.CurrentUser;
import george.javawebemail.ConstantFilters.JsonFilterNameConstants;
import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.Utilities.BeanJsonTransformer;

@Controller
@RequestMapping(value = "/api/emailController")
@Component
@CrossOrigin
public class EmailController {

    @Autowired
    private EmailService emailServiceObject;

    /**
     * Method to get all the given emails for a user's account
     * 
     * @param UserName
     * @param id
     * @return
     */
    // TODO finish building the api and test in insomnia
    @RequestMapping(value = "getEmails", method = RequestMethod.GET)
    public Response getAllEmailsByUser(@RequestParam Long id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returnStatusCode = 200;
        try {
            // TODO add functional code to complete the given work
            User currentUser = CurrentUser.currentLoggedOnUser;
            if (currentUser != null) {
                List<Email> allEmailsAsked = emailServiceObject.findEmailsByEmailIdAndUser(id, currentUser);
                HashMap<String, HashSet<String>> filterHashAndSet = new HashMap<String, HashSet<String>>();
                filterHashAndSet.put(JsonFilterNameConstants.EMAIL_FILTER_NAME,
                        JsonFilterConstants.EMAIL_REQUIRED_PROPERTIES);
                filterHashAndSet.put(JsonFilterNameConstants.USERS_FILTER_NAME,
                        JsonFilterConstants.USERS_OPTIONAL_UNIDENTIFIABLE_PROPERTIES);
                filterHashAndSet.put(JsonFilterNameConstants.CC_FILTER_NAME,
                        JsonFilterConstants.CC_REQUIRED_PROPERTIES);
                filterHashAndSet.put(JsonFilterNameConstants.BCC_FILTER_NAME,
                        JsonFilterConstants.BCC_REQUIRED_PROPERTIES);
                filterHashAndSet.put(JsonFilterNameConstants.RECEIVERS_FILTER_NAME,
                        JsonFilterConstants.RECEIVERS_REQUIRED_PROPERTIES);
                filterHashAndSet.put(JsonFilterNameConstants.ATTACHMENT_FILTER_NAME,
                        JsonFilterConstants.ATTACHMENT_REQUIRED_PROPERTIES);

                String jsonStringEmailsToSendToFront = BeanJsonTransformer
                        .multipleObjectsToJsonStringWithFilters(allEmailsAsked, filterHashAndSet);

            } else {
                returningHashMap.clear();
                returningHashMap.put("message", "please Log in to view this information");
                returnStatusCode = 200;
            }
        } catch (Exception e) {
            returningHashMap.clear();
            e.printStackTrace();
            Class classNameForIfs = e.getClass();
            if (classNameForIfs.isAssignableFrom(SQLException.class)) {
                returnStatusCode = 500;
                returningHashMap.put("message", "Error in the database, please try again later");

            } else if (classNameForIfs.isAssignableFrom(NullPointerException.class)) {
                returnStatusCode = 404;
                returningHashMap.put("message", "Null was found, please try again later");
            } else {
                returnStatusCode = 500;
                returningHashMap.put("message", "unexplained error was thrown, please try again later");
            }
        }
        return Response.status(returnStatusCode).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    @RequestMapping(value = "deleteEmail", method = RequestMethod.DELETE)
    public Response deleteGivenEmailById(@RequestBody Long id) {
        return null;
    }

}