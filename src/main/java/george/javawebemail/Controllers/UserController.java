/**
 * 
 * 
 */

package george.javawebemail.Controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.HashSet;

import george.javawebemail.Utilities.BeanJsonTransformer;
import george.javawebemail.Utilities.CurrentUser;
import george.javawebemail.Utilities.PlainTextToHashUtil;
import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.ConstantFilters.JsonFilterNameConstants;
import george.javawebemail.Entities.User;
import george.javawebemail.Service.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@Component
@CrossOrigin
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserService userServiceObject;

    /**
     * Method to register a given user by creating the user in the database example
     * for api call
     * 
     * date format should be: dd/MM/yyyy
     * 
     * @param userParameters
     * @return
     * @author gIlias
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.PUT)
    @ResponseBody
    public Response registerUser(@RequestBody HashMap<String, Object> userParameters) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        try {
            if (CurrentUser.currentLoggedOnUser == null) {
                if (userParameters.get("portChosen") == null) {
                    userParameters.put("portChosen", new String("25"));
                }
                // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("DD/MM/YYYY");
                // userParameters.put("createdAt", dtf.format(LocalDateTime.now()).toString());
                String passwordHash = PlainTextToHashUtil
                        .addSaltAndConvert(userParameters.get("passwordUnHash").toString());
                userParameters.remove("passwordUnHash");
                userParameters.put("passwordHash", passwordHash);
                User userToRegister = new ObjectMapper().readValue(new Gson().toJson(userParameters), User.class);
                User userToReturn = userServiceObject.saveUser(userToRegister);
                HashMap<String, HashSet<String>> filterToAdd = new HashMap<String, HashSet<String>>();
                filterToAdd.put(JsonFilterNameConstants.USERS_FILTER_NAME,
                        JsonFilterConstants.USERS_REQUIRED_PROPERTIES);
                filterToAdd.put(JsonFilterNameConstants.EMAIL_ACCOUNT_FILTER_NAME,
                        JsonFilterConstants.EMAILACCOUNTS_ALL_PROPERTIES);
                filterToAdd.put(JsonFilterNameConstants.EMAIL_FILTER_NAME,
                        JsonFilterConstants.EMAIL_REQUIRED_PROPERTIES);
                String userString = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(userToReturn,
                        filterToAdd);
                CurrentUser.currentLoggedOnUser = userToReturn;

                return Response.status(200).entity(userString).type(MediaType.APPLICATION_JSON).build();
            } else {
                returningHashMap.put("message",
                        "The user is already logged in, please log out of this account to log into another");
                return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
            }

        } catch (Exception e) {
            if (e.getClass() == SQLIntegrityConstraintViolationException.class) {
                returningHashMap.clear();
                e.printStackTrace();
                returningHashMap.put("message", "userName already in use please try again");
            } else {
                e.printStackTrace();
            }

        }
        if (returningHashMap.size() == 0) {
            returningHashMap.put("message",
                    "The user is already logged in, please log out of this account to log into another");
        }
        return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();

    }

    /**
     * method to try and login, first version will be using a plain text username
     * and password but subsequent versions will be using an encrypted username and
     * password using public key incryption i.e a public key will be used to encrypt
     * and a private key will be used to decrypt the given information
     * 
     * 
     * @param userName
     * @param attemptedPassword
     * @return Response
     * @author gIlias
     */
    @RequestMapping(value = "/loginRequest", method = RequestMethod.POST)
    @ResponseBody
    public Response loginUserWithResponse(@RequestBody String userName, @RequestBody String attemptedPassword) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        if (CurrentUser.currentLoggedOnUser == null) {
            try {

                User userToReturn = userServiceObject.findUserByUserNameAndPasswordHash(userName,
                        PlainTextToHashUtil.addSaltAndConvert(attemptedPassword));
                if (userToReturn != null) {
                    boolean arePasswordsTheSame = PlainTextToHashUtil.compareHashToPlain(userToReturn.getPasswordHash(),
                            attemptedPassword);

                    if (arePasswordsTheSame) {
                        String jsonData = BeanJsonTransformer.singleObjectToJsonStringWithFilters(userToReturn,
                                "userFilter", JsonFilterConstants.USERS_OPTIONAL_LOGIN_PROPERTIES);
                        CurrentUser.currentLoggedOnUser = userToReturn;
                        return Response.status(202).entity(jsonData).build();
                    } else {
                        returningHashMap.put("message",
                                "the username/password combination is incorrect, please try again");
                        return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
                    }

                } else {
                    throw new NullPointerException();
                }

            } catch (Exception e) {
                e.printStackTrace();
                returningHashMap.put("message", "Error when handling the login");
                return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
            }

        } else {
            returningHashMap.put("message",
                    "The user is already logged on, please log out if you would like to log in to a different account");
            return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
        }

    }

    /**
     * method to return a message depending on if the logout was successful or not
     * 
     * 
     * @author gIlias
     * @return
     */
    // the response returns things properly in this method, please use this for the
    // following rest apis as an example
    @RequestMapping(value = "/logoutRequest", method = RequestMethod.GET)
    @ResponseBody
    public Response logoutMethod(@RequestBody String id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        try {
            returningHashMap.clear();
            if (id != null) {
                returningHashMap.put("message", "success,you have been logged out");
            } else {
                returningHashMap.put("message", "No user was logged in");
            }
            return Response.status(200).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();

        } catch (Exception e) {
            e.printStackTrace();
            returningHashMap.put("message", "Error, the request has returned an error, please try again later");
            return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
        }
    }
}