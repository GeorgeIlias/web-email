package george.javawebemail.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.Attachment;

@Repository
@Component
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    @Query("select a from attachment where a.attachedTable.emailUserTable.id = :userId")
    public Iterable<Attachment> findAll(Long userId);

    @Query("select a from attachment a where a.id = :id and a.attachedTable.emailUserTable.id = :userId ")
    public Optional<Attachment> findById(Long id, Long userId);

    @Query("delete a from attachment a where a.id = :id and a.attachedTable.emailUserTable.id = :userId ")
    public void delete(Long id, Long userId);

    public Attachment save(Attachment attachment);
}