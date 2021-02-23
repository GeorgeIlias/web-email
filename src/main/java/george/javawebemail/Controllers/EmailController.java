/**
 * Controller get api responses for the emails
 * 
 * @author gIlias
 */

package george.javawebemail.Controllers;

import george.javawebemail.Entities.*;
import george.javawebemail.Exceptions.IncorrectDatabaseResponse;
import george.javawebemail.Exceptions.NoDatabaseObject;
import george.javawebemail.SendingReceiving.Receive;
import george.javawebemail.Service.*;

import george.javawebemail.ConstantFilters.*;
import george.javawebemail.Utilities.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
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
// TODO add a folders function for the email beans
public class EmailController<T extends Object> {

    @Autowired
    private EmailService emailServiceObject;

    @Autowired
    private EmailAccountService emailAccountServiceObject;

    @Autowired
    private UserService userServiceObject;

    @Autowired
    public ValueOperations<String, String> lo;

    /**
     * Method to get all the given emails for a user's account
     * 
     * @param UserName
     * @param id
     * @return
     */
    @RequestMapping(value = "/getEmailsFromDatabase", method = RequestMethod.POST)
    public Response getEmailByIdAndUser(@RequestBody Long id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returnStatusCode = 200;
        try {
            User currentUser = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(new Gson().toJson(lo.get("user")), User.class);

            if (currentUser != null) {
                List<Email> allEmailsAsked = emailServiceObject.findEmailsByEmailIdAndUser(id, currentUser);

                String jsonStringEmailsToSendToFront = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(
                        allEmailsAsked,
                        PropertyReturnTypesForControllers.EmailControllerProperties.returnTypicalPropertiesForEmail());

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
    @RequestMapping(value = "/getEmailByUserFromDatabase", method = RequestMethod.GET)
    public Response getEmailsByUser(@RequestBody Long userId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        User currentLoggedInUser = userServiceObject.findById(userId);
        int returningStatusNumber = 200;
        if (currentLoggedInUser != null) {
            try {
                List<Email> listOfExistingEmails = emailServiceObject.findAllByUSer(currentLoggedInUser.getId());
                String jsonEmails = BeanJsonTransformer.listMultipleObjectToJsonStringWithMultipleFilters(
                        listOfExistingEmails,
                        PropertyReturnTypesForControllers.EmailControllerProperties.returnTypicalPropertiesForEmail());
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
     * @param userId
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/createEmail", method = RequestMethod.PUT)
    public Response createEmail(@RequestBody HashMap<String, Object> parameters, @RequestParam long userId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int statusNumber = 200;
        try {
            User userToCheck = userServiceObject.findById(userId);

            if (userToCheck != null) {
                Email emailToSave = emailServiceObject
                        .save(new ObjectMapper().readValue(new Gson().toJson(parameters), Email.class));
                emailToSave.setUserSent(userToCheck);
                // TODO find out a better way to get the user from the sending form
                Email emailToReturn = emailServiceObject.save(emailToSave);
                String jsonStringToReturn = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(emailToReturn,
                        PropertyReturnTypesForControllers.EmailControllerProperties.returnTypicalPropertiesForEmail());
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

    /**
     * method to receive emails from the email service that the client is using i.e
     * gmail/yahoo/hotmail etc.
     * 
     * 02/21 added folder names for the actual folder repository that will be added.
     * 
     * @author gIlias
     * @param folderName
     * @param emailAccountId
     * @return
     */
    @RequestMapping(value = "/getEmailsFromEmailAccountService", method = RequestMethod.GET)
    public Response getEmailsFromEmailAccountService(@RequestParam Long emailAccountId,
            @RequestParam(required = false, defaultValue = "null") String folderName) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int statusNumber = 200;
        // defaults to inbox, will add a feature for changing the folders later
        String inboxString = "inbox";
        if (folderName != "null") {
            inboxString = folderName;
        }
        try {
            if (getUserFromRedis() != null) {
                EmailAccount emailAccountToGetItemsFrom = emailAccountServiceObject
                        .findEmaillAcountById(emailAccountId);
                if (emailAccountToGetItemsFrom != null) {
                    List<Email> returningEmails = Receive.getEmailsFromInboxFolder(emailAccountToGetItemsFrom,
                            inboxString);
                    String emailJson = BeanJsonTransformer.listMultipleObjectToJsonStringWithMultipleFilters(
                            returningEmails, PropertyReturnTypesForControllers.EmailControllerProperties
                                    .returnTypicalPropertiesForEmail());
                    statusNumber = 200;
                    return Response.status(statusNumber).entity(emailJson).type(MediaType.APPLICATION_JSON).build();
                }
            }
        } catch (MessagingException me) {
            me.printStackTrace();
            statusNumber = 400;
            returningHashMap.put("message", "error receiving the messages");

        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            statusNumber = 404;
            returningHashMap.put("message", "Error receiving the emails you are looking for");

        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();

        }
        return Response.status(statusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * method to use the priate method instead of continuously re-writting this code
     * 
     * @author gIlias
     * @return
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    private User getUserFromRedis() {
        User currentUser = null;
        try {
            currentUser = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(new Gson().toJson(lo.get("user")), User.class);
        } catch (JsonMappingException jme) {
            jme.printStackTrace();
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentUser;

    }

}