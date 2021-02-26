/**
 * 
 * 
 */

package george.javawebemail.Controllers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import george.javawebemail.Utilities.BeanJsonTransformer;
import george.javawebemail.Utilities.PlainTextToHashUtil;
import george.javawebemail.Utilities.PropertyReturnTypesForControllers;
import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.Controllers.Helper.RedisHelper;
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

    // @Resource // (name = "listOperationsCaster")

    @Autowired
    private RedisHelper helper;

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
            if (userParameters.get("portChosen") == null) {
                userParameters.put("portChosen", new String("25"));
            }
            String passwordHash = PlainTextToHashUtil
                    .addSaltAndConvert(userParameters.get("passwordUnHash").toString());
            userParameters.remove("passwordUnHash");
            userParameters.put("passwordHash", passwordHash);

            User userToCheckAgainst = userServiceObject
                    .findUserByUserNameAndPasswordHash(String.valueOf(userParameters.get("userName")), passwordHash);

            if (userToCheckAgainst == null) {
                User userToRegister = new ObjectMapper()
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                        .readValue(new Gson().toJson(userParameters), User.class);
                User userToReturn = userServiceObject.saveUser(userToRegister);
                String userString = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(userToReturn,
                        PropertyReturnTypesForControllers.UserControllerProperties.returnTypicalPropertiesForUser());
                helper.setUser(userToRegister);
                return Response.status(200).entity(userString).type(MediaType.APPLICATION_JSON).build();
            } else {
                returningHashMap.put("message", "The user already exists, please try again");
                return Response.status(404).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
            }

        } catch (Exception e) {
            if (e.getClass() == SQLIntegrityConstraintViolationException.class) {
                returningHashMap.clear();
                e.printStackTrace();
                returningHashMap.put("message", "username already in use please try again");
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
     * @param map
     * @return Response
     * @author gIlias
     */
    @RequestMapping(value = "/loginRequest", method = RequestMethod.POST)
    @ResponseBody
    public Response loginUserWithResponse(@RequestBody HashMap<String, String> map) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        try {
            User userToCheckAgainst = helper.getUser();
            if (userToCheckAgainst == null) {
                String attemptedPassword = map.get("attemptedPassword");
                String userName = map.get("userName");

                User userToReturn = userServiceObject.findUserByUserNameAndPasswordHash(userName,
                        PlainTextToHashUtil.addSaltAndConvert(attemptedPassword));
                if (userToReturn != null) {
                    boolean arePasswordsTheSame = PlainTextToHashUtil.compareHashToPlain(userToReturn.getPasswordHash(),
                            attemptedPassword);

                    if (arePasswordsTheSame) {
                        String jsonData = BeanJsonTransformer.singleObjectToJsonStringWithFilters(userToReturn,
                                "userFilter", JsonFilterConstants.USERS_OPTIONAL_LOGIN_PROPERTIES);

                        return Response.status(202).entity(jsonData).build();
                    } else {
                        returningHashMap.put("message",
                                "the username/password combination is incorrect, please try again");
                        // add the user here please
                        return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
                    }

                } else {
                    returningHashMap.put("message",
                            "A user is already looged in to this session, please logout if you want to log in using a different user");
                    return Response.status(404).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();

                }

            } else {
                returningHashMap.put("message",
                        "The user is already logged on, please log out if you would like to log in to a different account");
                return Response.status(400).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            returningHashMap.put("message", "null was found where it shouldn't have been found");
            return Response.status(404).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            returningHashMap.put("message", "Error when handling the login");
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
    public Response logoutMethod(@RequestBody Long id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        try {
            returningHashMap.clear();
            if (id != null) {
                User userToCheck = helper.getUser();
                if (userToCheck != null && userToCheck.getId() == id) {
                    returningHashMap.put("message", "success,you have been logged out");
                    // there seems not to be a delete for the value operations so setting to null is
                    // the only choice here.
                    helper.setUser(null);
                } else {
                    throw new Exception();
                }
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