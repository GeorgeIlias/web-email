/**
 * Controller get api responses for the emails
 * 
 * @author gIlias
 */

package george.javawebemail.Controllers;

import george.javawebemail.Entities.*;
import george.javawebemail.Service.*;

import george.javawebemail.ConstantFilters.*;
import george.javawebemail.Utilities.*;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//TODO test in insmonia for bugs
@Controller
@RequestMapping("/api/emailController")
@Component
@CrossOrigin
// class for the email controller, T extends Object is for testing for now to
// solve the type safety warning
public class EmailController<T extends Object> {

    @Autowired
    private EmailService emailServiceObject;

    /**
     * Method to get all the given emails for a user's account
     * 
     * @param UserName
     * @param id
     * @return
     */
    @RequestMapping(value = "/getEmails", method = RequestMethod.GET)
    public Response getEmailByIdAndUser(@RequestParam Long id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returnStatusCode = 200;
        try {
            // TODO add functional code to complete the given work
            User currentUser = CurrentUser.currentLoggedOnUser;
            if (currentUser != null) {
                List<Email> allEmailsAsked = emailServiceObject.findEmailsByEmailIdAndUser(id, currentUser);

                String jsonStringEmailsToSendToFront = BeanJsonTransformer
                        .multipleObjectsToJsonStringWithFilters(allEmailsAsked, returnTypicalPropertiesForEmail());

                return Response.status(200).entity(jsonStringEmailsToSendToFront).type(MediaType.APPLICATION_JSON)
                        .build();

            } else {
                returningHashMap.clear();
                returningHashMap.put("message", "please Log in to view this information");
                returnStatusCode = 500;
            }
        } catch (Exception e) {
            returningHashMap.clear();
            e.printStackTrace();
            Class<?> classNameForIfs = e.getClass();
            if (classNameForIfs.isAssignableFrom(SQLException.class)) {
                returnStatusCode = 500;
                returningHashMap.put("message", "Error in the database, please try again later");

            } else if (classNameForIfs.isAssignableFrom(NullPointerException.class)) {
                returnStatusCode = 404;
                returningHashMap.put("message", "Null was found, please try again later");
            } else {
                returnStatusCode = 500;
                returningHashMap.put("message", "Unknown error has occured, please try again later");
            }
        }
        return Response.status(returnStatusCode).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * method to delete a given email provided it exists
     * 
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/getEmailByUser", method = RequestMethod.GET)
    public Response getEmailsByUser() {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        User currentLoggedInUser = CurrentUser.currentLoggedOnUser;
        int returningStatusNumber = 200;
        if (currentLoggedInUser != null) {
            try {
                List<Email> listOfExistingEmails = emailServiceObject.findAllByUSer(currentLoggedInUser.getId());
                String jsonEmails = BeanJsonTransformer.listMultipleObjectToJsonStringWithMultipleFilters(
                        listOfExistingEmails, returnTypicalPropertiesForEmail());
                return Response.status(returningStatusNumber).entity(jsonEmails).type(MediaType.APPLICATION_JSON)
                        .build();
            } catch (Exception e) {
                returningStatusNumber = 500;
                returningHashMap.put("message", e.getMessage().toString());
            }
        } else {
            returningStatusNumber = 404;
            returningHashMap.put("message", "The user is not logged in");
        }
        return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    private HashMap<String, HashSet<String>> returnTypicalPropertiesForEmail() {
        HashMap<String, HashSet<String>> filterHashAndSet = new HashMap<String, HashSet<String>>();
        filterHashAndSet.put(JsonFilterNameConstants.EMAIL_FILTER_NAME, JsonFilterConstants.EMAIL_REQUIRED_PROPERTIES);
        filterHashAndSet.put(JsonFilterNameConstants.USERS_FILTER_NAME,
                JsonFilterConstants.USERS_OPTIONAL_UNIDENTIFIABLE_PROPERTIES);
        filterHashAndSet.put(JsonFilterNameConstants.CC_FILTER_NAME, JsonFilterConstants.CC_REQUIRED_PROPERTIES);
        filterHashAndSet.put(JsonFilterNameConstants.BCC_FILTER_NAME, JsonFilterConstants.BCC_REQUIRED_PROPERTIES);
        filterHashAndSet.put(JsonFilterNameConstants.RECEIVERS_FILTER_NAME,
                JsonFilterConstants.RECEIVERS_REQUIRED_PROPERTIES);
        filterHashAndSet.put(JsonFilterNameConstants.ATTACHMENT_FILTER_NAME,
                JsonFilterConstants.ATTACHMENT_REQUIRED_PROPERTIES);

        return filterHashAndSet;

    }

}