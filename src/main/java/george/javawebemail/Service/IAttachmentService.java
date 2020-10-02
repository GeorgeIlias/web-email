package george.javawebemail.Service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import george.javawebemail.Entities.Attachment;

public interface IAttachmentService {

    public List<Attachment> findAll();

    public Optional<Attachment> findById(Long id);

    public boolean createAttachment(Long emailId, byte[] attahcmentContent);

    public Pair<Boolean,String> deleteAttachment(Long attahcmentId);

    public List<Attachment> getAllAttachmentsByEmail(Long emailId);

}