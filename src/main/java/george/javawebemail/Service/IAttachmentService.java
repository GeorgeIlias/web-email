package george.javawebemail.Service;

import java.util.List;
import java.util.Optional;

import george.javawebemail.Entities.Attachment;

public interface IAttachmentService {

    public List<Attachment> findAll();

    public Optional<Attachment> findById(Long id);

    public boolean createAttachment(Long emailId, byte[] attahcmentContent);

    public boolean deleteAttachment(Long attahcmentId);

    public List<Attachment> getAllAttachmentsByEmail(Long emailId);

    

}