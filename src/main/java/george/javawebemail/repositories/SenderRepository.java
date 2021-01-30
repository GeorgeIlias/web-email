package george.javawebemail.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.Senders;

@Repository
public interface SenderRepository extends CrudRepository<Senders, Long> {
    public List<Senders> findAllById(Long id);

    public void delete(Senders entityToDelete);

    public Senders save(Senders entityToSave);

    //
    @Query(value = "select * from Senders s where s.senders_emails_table.id = ?1 and s.senders_emails_table.email_user_table_id.id = ?2", nativeQuery = true)
    public List<Senders> findAllSendersByIdAndUserId(Long emailId, Long userId);

}
