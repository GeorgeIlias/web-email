/**
 * Controller for error redirection
 * will be redirected to this to send a message from the server to the client if something goes wrong
 * more functionality will be added as the server gets more complicated
 * 
 * @author gIlias
 */

package george.javawebemail.Controllers;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Component
@CrossOrigin
@RequestMapping(value = "/api")
public class ErrorController {

    @GetMapping(value = "/error")
    @ResponseBody
    public Response returnErrorForFiveHundred(@RequestParam(value = "message", required = true) String message) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        returningHashMap.put("message", "error; Routing from " + message.toString());
        return Response.status(500).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

}
