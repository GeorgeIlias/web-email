package george.javawebemail.Controllers;

import java.util.HashMap;
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
    @RequestMapping(value = "getEmails", method = RequestMethod.GET)
    public Response getAllEmailsByUser(@RequestParam Long id) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returnStatusCode = 200;
        try {
            // TODO add functional code to complete the given work
            User currentUser = CurrentUser.currentLoggedOnUser;
            if (currentUser != null) {
                List<Email> allEmailsAskedFor = emailServiceObject.findEmailsByEmailIdAndUser(id, currentUser);
                
            } else {
                returningHashMap.clear();
                returningHashMap.put("message", "please Log in to view this information");
                returnStatusCode = 200;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            returningHashMap.clear();
            returningHashMap.put("message", "Null was found, please try again later");
        }
        return Response.status(returnStatusCode).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    @RequestMapping(value = "deleteEmail", method = RequestMethod.DELETE)
    public Response deleteGivenEmailById(@RequestBody Long id) {
        return null;
    }

}