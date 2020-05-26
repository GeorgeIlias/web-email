package george.javawebemail.Controllers;

import java.util.HashMap;
import java.util.HashSet;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import george.javawebemail.repositories.AttachmentRepository;
import george.javawebemail.Entities.Attachment;
import george.javawebemail.Utilities.BeanJsonTransformer;
import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.Utilities.CurrentUser;

@Controller
@Component
public class AttachmentController {

    private AttachmentRepository attachmentRepository;

    @RequestMapping(value = "/getAttachment", method = RequestMethod.GET)
    public Response findAttachmentById(@RequestParam Long attachmentId) {
        if (CurrentUser.currentLoggedOnUser != null) {
            try {
                Attachment currentAttachment = attachmentRepository
                        .findById(attachmentId, CurrentUser.currentLoggedOnUser.getId()).get();

                HashMap<String, HashSet<String>> hashMapSet = new HashMap<String, HashSet<String>>();
                hashMapSet.put("attachmentFilter", new HashSet<String>(JsonFilterConstants.ATTACHMENT_ALL_PROPERTIES));
                hashMapSet.put("emailFilter", new HashSet<String>(JsonFilterConstants.EMAIL_ALL_PROPERTIES));

                String jsonData = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(currentAttachment,
                        hashMapSet);
                return Response.status(Status.OK).entity(jsonData).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Status.BAD_REQUEST).entity(new HashMap<String, String>().put("message",
                        "an error has occured or an attachment could not be found")).build();
            }
        } else {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new HashMap<String, String>().put("message", "No user has been logged on")).build();
        }
    }
}