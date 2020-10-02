package george.javawebemail.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.Attachment;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    @Query(value = "select * from Attachment a where a.attachedTable.emailUserTable.id= :userId", nativeQuery = true)
    public Iterable<Attachment> findAll(@Param("userId") Long userId);

    // @Query("select a from Attachment a where a.id = :id and
    // a.attachedTable.emailUserTable.id=:userId")
    // public Optional<Attachment> findByIdAndUser(@Param("id") Long id,
    // @Param("userId") Long userId);

    public Optional<Attachment> findById(Long id);

    // TODO add an actual working query for this
    @Query(value = "select * from attachment", nativeQuery = true)
    public Optional<Attachment> findByIdAndUserId(Long id, Long currentUserId);

    public void delete(Attachment entityToDelete);

    public Attachment save(Attachment attachment);
}