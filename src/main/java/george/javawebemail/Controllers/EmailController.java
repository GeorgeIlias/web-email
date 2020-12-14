/**
 * Controller get api responses for the emails
 * 
 * @author gIlias
 */

package george.javawebemail.Controllers;

import george.javawebemail.Entities.*;
import george.javawebemail.Exceptions.IncorrectDatabaseResponse;
import george.javawebemail.Exceptions.NoDatabaseObject;
import george.javawebemail.Service.*;

import george.javawebemail.ConstantFilters.*;
import george.javawebemail.Utilities.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private UserService userServiceObject;

    /**
     * Method to get all the given emails for a user's account
     * 
     * @param UserName
     * @param id
     * @return
     */
    @RequestMapping(value = "/getEmails", method = RequestMethod.POST)
    public Response getEmailByIdAndUser(@RequestBody Long id, @RequestBody Long userId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returnStatusCode = 200;
        try {
            User currentUser = userServiceObject.findById(userId);
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
     * @param userId
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/getEmailByUser", method = RequestMethod.GET)
    public Response getEmailsByUser(@RequestBody Long userId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        User currentLoggedInUser = userServiceObject.findById(userId);
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
                returningHashMap.put("message", "an error has occured with the server, please be patient.");
            }
        } else {
            returningStatusNumber = 404;
            returningHashMap.put("message", "The user is not logged in.");
        }
        return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * Method to create an email given the user is logged in, will either return a
     * hash map with the message of what went wrong or the entity you were trying to
     * create
     * 
     * 
     * 
     * @param parameters
     * @param id
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/createEmail", method = RequestMethod.PUT)
    public Response createEmail(@RequestBody HashMap<String, Object> parameters, @RequestParam long id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int statusNumber = 200;
        try {
            User userToCheck = userServiceObject.findById(id);

            if (userToCheck != null) {
                Email emailToSave = emailServiceObject
                        .save(new ObjectMapper().readValue(new Gson().toJson(parameters), Email.class));
                emailToSave.setUserSent(userToCheck);
                // TODO find out a better way to get the user from the sending form
                Email emailToReturn = emailServiceObject.save(emailToSave);
                String jsonStringToReturn = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(emailToReturn,
                        returnTypicalPropertiesForEmail());
                return Response.status(statusNumber).entity(jsonStringToReturn).type(MediaType.APPLICATION_JSON)
                        .build();
            }

        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();
            returningHashMap.put("message", "The server has not responded normally, please try again later");
            statusNumber = 500;
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            statusNumber = 404;
            returningHashMap.put("message",
                    "The user you are looking for does not exist in the database, please try again later.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(statusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();

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