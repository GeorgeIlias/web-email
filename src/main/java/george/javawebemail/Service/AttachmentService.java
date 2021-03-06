package george.javawebemail.Service;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import george.javawebemail.Controllers.Helper.RedisHelper;
import george.javawebemail.Entities.Attachment;
import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;
import george.javawebemail.repositories.AttachmentRepository;
import george.javawebemail.repositories.EmailRepository;
import javassist.tools.rmi.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService implements IAttachmentService {

  @Autowired
  private AttachmentRepository attachmentRepoObject;

  @Autowired
  private EmailRepository emailRepoObject;

  @Autowired
  private RedisHelper redisHelper;

  @Override
  public List<Attachment> findAll() {
    return (List<Attachment>) attachmentRepoObject.findAll();

  }

  @Override
  public Optional<Attachment> findById(Long id) {
    return attachmentRepoObject.findById(id);
  }

  /**
   * Creates an attachment for the email in the database
   * 
   * Works with an already existing email
   * 
   * @param emailId
   * @param attachment
   * @author gIlias
   */
  @Override
  public boolean createAttachment(Long emailId, byte[] attachment) {
    try {
      Attachment currentAttachmentToSend = new Attachment();
      Email emailIdToSend = emailRepoObject.findById(emailId).get();
      currentAttachmentToSend.setAttached(emailIdToSend);
      currentAttachmentToSend.setAttachment(attachment);

      attachmentRepoObject.save(currentAttachmentToSend);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }

  }

  /**
   * Method to delete the attachment, will first find the attachment in the
   * databse then delete it from the given database and save the email
   * 
   * @param attachmentId
   * @author gIlias
   */
  @Override
  public Pair<Boolean, String> deleteAttachment(Long attachmentId) {
    Pair<Boolean, String> pairToReturn;
    try {
      Attachment givenAttachment = attachmentRepoObject.findById(attachmentId).get();
      if (givenAttachment != null) {
        User attachmentUser = givenAttachment.getAttached().getUserSent();
        if (attachmentUser == redisHelper.getUser()) {
          attachmentRepoObject.delete(givenAttachment);
          emailRepoObject.save(givenAttachment.getAttached());
          pairToReturn = new ImmutablePair<>(true, "the attachment was deleted");
          return pairToReturn;
        } else {
          throw new NullPointerException("The  user given does not match the user that created this attachment");
        }
      } else {
        throw new ObjectNotFoundException("The chosen object has not been found");
      }
    } catch (Exception e) {
      e.printStackTrace();
      String messageToReturn = e.getMessage();
      pairToReturn = new ImmutablePair<>(false, messageToReturn);
      return pairToReturn;
    }

  }

  /**
   * method to get all the attachment of a given email from the database
   * 
   * @author gIlias
   * @param emailId
   */
  @Override
  public List<Attachment> getAllAttachmentsByEmail(Long emailId) {
    try {
      return emailRepoObject.findById(emailId).get().getAttachmentList();
    } catch (Exception e) {
      e.printStackTrace();
      throw new NullPointerException();
    }
  }

}