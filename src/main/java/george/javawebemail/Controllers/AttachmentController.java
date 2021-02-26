/**
 * Class to control how the server serves attachments to the client
 * @author gIlias
 */
package george.javawebemail.Controllers;

import java.util.HashMap;
import java.util.HashSet;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import george.javawebemail.Entities.Attachment;
import george.javawebemail.Entities.Email;
import george.javawebemail.Service.AttachmentService;
import george.javawebemail.Service.EmailService;
import george.javawebemail.Service.UserService;
import george.javawebemail.Utilities.BeanJsonTransformer;
import george.javawebemail.Controllers.Helper.RedisHelper;
import george.javawebemail.Utilities.PropertyReturnTypesForControllers;

@Controller
@RequestMapping("/api/Attachment")
@Component
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentServiceRepo;

    @Autowired
    private UserService userServiceObject;

    @Autowired
    private EmailService emailServiceObject;

    @Autowired
    private RedisHelper redisHelper;

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
    public Response findAttachmentById(@RequestParam Long attachmentId, @RequestParam Long emailId,
            @RequestParam String cookieHash) {
        if (redisHelper.getUser() != null) {
            try {
                Email emailToUse = emailServiceObject.findByIdAndByUserSent(emailId,
                        userServiceObject.findUserByCookieHash(cookieHash));
                Attachment currentAttachment = null;

                for (Attachment currentAttachmentInLoop : emailToUse.getAttachmentList()) {
                    if (currentAttachmentInLoop.getId() == attachmentId) {
                        currentAttachment = currentAttachmentInLoop;
                    }
                }

                HashMap<String, HashSet<String>> hashMapSet = PropertyReturnTypesForControllers.AttachmentControllerProperties
                        .returnTypicalPropertiesForAttachment();

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
    @ResponseBody
    public Response deleteGivenAttachment(@RequestBody Long attachmentId, @RequestBody Long emailId,
            @RequestBody String cookieHash) {
        HashMap<String, String> returningHashMap = new HashMap<String, String>();
        int returningStatusNumber = 200;
        Email emailToUse = emailServiceObject.findByIdAndByUserSent(emailId,
                userServiceObject.findUserByCookieHash(cookieHash));
        if (emailToUse != null) {
            Attachment currentAttachment = null;
            for (Attachment currentAttachmentInLoop : emailToUse.getAttachmentList()) {
                if (currentAttachmentInLoop.getId() == attachmentId) {
                    currentAttachment = currentAttachmentInLoop;
                }
            }
            if (currentAttachment != null) {
                try {
                    Pair<Boolean, String> checkForAttachment = attachmentServiceRepo
                            .deleteAttachment(currentAttachment.getId());
                    if (checkForAttachment.getKey() == false) {
                        returningStatusNumber = 404;
                        returningHashMap.put("message", checkForAttachment.getValue());
                    } else {
                        returningStatusNumber = 200;
                        returningHashMap.put("message", checkForAttachment.getValue());
                    }

                } catch (Exception e) {
                    returningHashMap.put("message",
                            "An error occured while trying to search for the given attachment to delete");
                    returningStatusNumber = 500;
                    return Response.status(returningStatusNumber).entity(returningHashMap)
                            .type(MediaType.APPLICATION_JSON).build();
                }
            }

        } else {
            returningHashMap.put("message", "Please log in to use this feature");
            return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON)
                    .build();
        }
        return Response.status(returningStatusNumber).entity(returningHashMap).type(MediaType.APPLICATION_JSON).build();
    }

    /**
     * creation api end point for the attachment objects Will take a parameter list
     * and the key for the byte will be byteAttachment
     * 
     * @param parameterList
     * @return
     */
    // TODO finish attachment creation proccess
    @RequestMapping(value = "/createAttachment", method = RequestMethod.PUT)
    @ResponseBody
    public Response createAttachment(@RequestBody HashMap<String, String> parameterList, @RequestBody long emailId) {
        int statusNumber = 200;
        HashMap<String, String> returnMessage = new HashMap<String, String>();
        try {
            try {

                byte[] currentAttachment = parameterList.get("attachment").toString().getBytes();
                // Attachment currentAttachment = new ObjectMapper().readValue(new
                // Gson().toJson(parameterList),
                // Attachment.class);

                // will take the email that you are trying to write and will check if it is
                // saved. if it is then the attachment will be added to it and then the code
                // will play out.
                Email currentEmailToUser = emailServiceObject.findById(emailId);
                if (currentEmailToUser == null) {
                    returnMessage.clear();
                    returnMessage.put("message",
                            "An email doesn't exist, please start one before trying to add an attachment");
                    statusNumber = 500;
                } else {
                    if (currentAttachment != null) {
                        if (!checkForGivenAttachment(currentAttachment, currentEmailToUser)) {
                            // TODO add create no seriously add create
                            boolean isCreated = attachmentServiceRepo.createAttachment(currentEmailToUser.getId(),
                                    currentAttachment);
                        }
                    }

                }

            } catch (Exception roughTopLevelException) {
                roughTopLevelException.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return Response.status(400).entity(returnMessage).type(MediaType.APPLICATION_JSON).build();

    }

    /**
     * Helper method to look for an attachment in the current email and add it to
     * the list of attachments for that email, if it doesn't already exist in that
     * email.
     * 
     * 
     * @author gIlias
     * @param attachmentToCheckFor
     */
    private boolean checkForGivenAttachment(byte[] attachmentToCheckFor, Email existingEmailFound) {
        boolean booleanToReturn = false;
        booleanToReturn = existingEmailFound.getAttachmentList().stream()
                .anyMatch((o) -> o.getAttachment().equals(attachmentToCheckFor));
        return booleanToReturn;
    }
}