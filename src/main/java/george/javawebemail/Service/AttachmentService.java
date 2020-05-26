package george.javawebemail.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import george.javawebemail.Entities.Attachment;
import george.javawebemail.Entities.Email;
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
  public boolean deleteAttachment(Long attachmentId) {
    try {
      Attachment givenAttachment = attachmentRepoObject.findById(attachmentId).get();
      if (givenAttachment != null) {
        attachmentRepoObject.delete(givenAttachment);
        emailRepoObject.save(givenAttachment.getAttached());
        return true;
      } else {
        throw new ObjectNotFoundException("the chosen object has not been found");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
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