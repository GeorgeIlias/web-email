package george.javawebemail.Controllers;

import java.util.HashMap;
import java.util.HashSet;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.ConstantFilters.JsonFilterNameConstants;
import george.javawebemail.Entities.EmailAccount;
import george.javawebemail.Entities.User;
import george.javawebemail.Exceptions.IncorrectDatabaseResponse;
import george.javawebemail.Exceptions.NoDatabaseObject;
import george.javawebemail.Service.EmailAccountService;
import george.javawebemail.Service.UserService;
import george.javawebemail.Utilities.BeanJsonTransformer;

@Controller
@RequestMapping("/api/EmailAccountController")
@Component
public class EmailAccountController {

    @Autowired
    private EmailAccountService emailAccountServiceObject;

    @Autowired
    private UserService userServiceObject;

    /**
     * Method to get the current email account once you are already logged in
     * 
     * @param userId
     * @param emailId
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/getCurrentEmailAccount", method = RequestMethod.GET)
    public Response getCurrentEmailAccount(@RequestParam Long userId, @RequestParam Long emailId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int statusNumber = 200;
        try {

            User currentUser = userServiceObject.findById(userId);

            if (currentUser != null) {
                EmailAccount returningEmailAccount = emailAccountServiceObject.findByUserEmailAccountAndId(emailId,
                        userId);

                if (returningEmailAccount != null) {
                    String returnString = BeanJsonTransformer
                            .multipleObjectsToJsonStringWithFilters(returningEmailAccount, getReturnsDefault());

                    return Response.status(statusNumber).entity(returnString).type(MediaType.APPLICATION_JSON).build();
                }
            }

        } catch (NoDatabaseObject ndo) {
            statusNumber = 500;
            returningHashMap.put("message",
                    "The email account that you are looking for does not exist, please try again.");
            ndo.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(statusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();

    }

    /**
     * Creates an email account and then saves it to the database
     * 
     * @param mapToUse
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/createEmailAccount", method = RequestMethod.PUT)
    public Response createEmailAccount(@RequestBody HashMap<String, Object> mapToUse, @RequestParam long userId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int statusNumber = 200;
        try {
            User userToSaveWith = userServiceObject.findById(userId);

            if (userToSaveWith != null) {
                EmailAccount accountToSave = new ObjectMapper().readValue(new Gson().toJson(mapToUse),
                        EmailAccount.class);

                accountToSave.setUserEmailAccounts(userToSaveWith);
                EmailAccount accountToReturn = emailAccountServiceObject.saveEmailAccount(accountToSave);
                if (accountToReturn != null) {
                    String stringToReturn = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(accountToReturn,
                            getReturnsDefault());
                    return Response.status(statusNumber).entity(stringToReturn).type(MediaType.APPLICATION_JSON)
                            .build();

                } else {
                    throw new NoDatabaseObject();
                }

            }

        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            returningHashMap.put("message", "There is a problem with the database and and object could not be found.");
            statusNumber = 500;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(statusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();

    }

    /**
     * Deletes a given email account from the database, returns a boolean to this
     * method if the boolean is true than 200 will be returned else an error status
     * 
     * @param emailAccount
     * @param userId
     * @author gIlias
     * @return
     */
    @RequestMapping(value = "/deleteEmailAccount", method = RequestMethod.DELETE)
    public Response deleteEmailAccount(@RequestBody long emailAccount, @RequestBody long userId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int statusNumber = 200;

        try {
            User userToCheckAgainst = userServiceObject.findById(userId);
            if (userToCheckAgainst != null) {
                EmailAccount emailAccountToDelete = emailAccountServiceObject.findEmaillAcountById(emailAccount);
                if (emailAccountToDelete != null) {
                    emailAccountServiceObject.deleteEmailAccount(emailAccountToDelete);

                }
            }
        } catch (NoDatabaseObject ndo) {
            statusNumber = 404;
            returningHashMap.put("message",
                    "The the object you are looking for does not exist, please try again later.");
            ndo.printStackTrace();

        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();
            statusNumber = 500;
            returningHashMap.put("message",
                    "The user you are looking for does not exist in the database, please try again later.");

        } catch (Exception e) {
            e.printStackTrace();

        }

        return Response.status(statusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    private HashMap<String, HashSet<String>> getReturnsDefault() {
        HashMap<String, HashSet<String>> returningObject = new HashMap<String, HashSet<String>>();
        returningObject.put(JsonFilterNameConstants.USERS_FILTER_NAME, JsonFilterConstants.USERS_ALL_PROPERTIES);
        return returningObject;
    }

    private HashMap<String, HashSet<String>> getReturnsOptionals() {
        HashMap<String, HashSet<String>> returningObject = new HashMap<String, HashSet<String>>();
        returningObject.put(JsonFilterNameConstants.USERS_FILTER_NAME, JsonFilterConstants.USERS_OPTIONAL_PROPERTIES);
        return returningObject;
    }
}