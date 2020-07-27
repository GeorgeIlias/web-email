package george.javawebemail.Controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.CheckedInputStream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import george.javawebemail.Entities.Attachment;
import george.javawebemail.Entities.Email;
import george.javawebemail.Service.AttachmentService;
import george.javawebemail.Service.EmailService;
import george.javawebemail.Utilities.BeanJsonTransformer;
import george.javawebemail.ConstantFilters.JsonFilterConstants;
import george.javawebemail.Utilities.CurrentUser;
import george.javawebemail.Entities.User;

@Controller
@Component
@RequestMapping(value = "/api/Attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentRepository;

    @Autowired
    private EmailService emailRepoObject;

    // TODO fix the find by id and get a given user, will probably be a weird query
    // to get working

    /**
     * Method to get a given attachemnt based on it's id and the user that is
     * currently logged into
     * 
     * @param attachmentId
     * @param emailId
     * @return
     * @author gIlias
     */
    @RequestMapping(value = "/getAttachment", method = RequestMethod.GET)
    public Response findAttachmentById(@RequestBody Long attachmentId, @RequestBody Long emailId) {
        if (CurrentUser.currentLoggedOnUser != null) {
            try {
                Email emailToUse = emailRepoObject.findByIdAndByUserSent(emailId, CurrentUser.currentLoggedOnUser);
                Attachment currentAttachment = null;

                for (Attachment currentAttachmentInLoop : emailToUse.getAttachmentList()) {
                    if (currentAttachmentInLoop.getId() == attachmentId) {
                        currentAttachment = currentAttachmentInLoop;
                    }
                }

                HashMap<String, HashSet<String>> hashMapSet = new HashMap<String, HashSet<String>>();
                hashMapSet.put("attachmentFilter", new HashSet<String>(JsonFilterConstants.ATTACHMENT_ALL_PROPERTIES));
                hashMapSet.put("emailFilter", new HashSet<String>(JsonFilterConstants.EMAIL_ALL_PROPERTIES));

                String jsonData = BeanJsonTransformer.multipleObjectsToJsonStringWithFilters(currentAttachment,
                        hashMapSet);
                return Response.status(200).entity(jsonData).type(MediaType.APPLICATION_JSON).build();
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

    /**
     * Method to delete a given attachment using a click from the front-end will
     * need to be logged in and will need to be on an given email that you are
     * composing. The last part will be done from the front end.
     * 
     * @author gIlias
     * @param attachment
     * @Return Response
     */
    // TODO add the body for this method specifically to delete a given attahchemnt
    @RequestMapping(value = "/deleteGivenAttachemnt", method = RequestMethod.DELETE)
    public Response deleteGivenAttachment(@RequestBody Long attachmentId) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returningStatusNumber = 200;
        User currentLoggedInUser = CurrentUser.currentLoggedOnUser;
        if (currentLoggedInUser != null) {
            try {
                boolean checkForAttachment = attachmentRepository.deleteAttachment(attachmentId);
                if (!checkForAttachment) {
                    returningStatusNumber = 404;
                    returningHashMap.put("message", "The attachment could not be found.");
                } else {
                    returningStatusNumber = 200;
                    returningHashMap.put("message", "The attachment has been deleted.");
                }

            } catch (Exception e) {
                returningHashMap.put("message",
                        "An error occured while trying to search for the given attachment to delete");
                returningStatusNumber = 500;
                return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON)
                        .build();
            }

        } else {
            returningHashMap.put("message", "Please log in to use this feature");
            return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }
}