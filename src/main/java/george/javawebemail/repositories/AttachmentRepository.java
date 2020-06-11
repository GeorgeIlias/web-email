package george.javawebemail.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.Attachment;

@Repository

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {

    // @Query("select a from Attachment a where a.attachedTable.emailUserTable.id= :userId")
    // public Iterable<Attachment> findAll(@Param("userId") Long userId);

    // @Query("select a from Attachment a where a.id = :id and a.attachedTable.emailUserTable.id=:userId")
    // public Optional<Attachment> findByIdAndUser(@Param("id") Long id, @Param("userId") Long userId);

    public Optional<Attachment> findById(Long id);

    public void delete(Attachment entityToDelete);

    public Attachment save(Attachment attachment);
}