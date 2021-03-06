/**
 * repository class fort the Email entity
 * 
 * @author gIlias
 */
package george.javawebemail.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.Email;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {

    @Query(value = "select distinct * from emails e where e.id = ?1 and e.email_user_table_id = ?2", nativeQuery = true)
    public Optional<Email> findByIdAndByEmailUserTableId(Long id, Long userSent);

    @Query(value = "select * from emails e where e.id = ?1 and e.email_user_table_id = ?2", nativeQuery = true)
    public List<Email> findAllByIdAndByEmailUserTableId(Long id, Long currentUser);

    @Query(value = "select * from emails where emails.email_user_table_id =?1", nativeQuery = true)
    public List<Email> findAllByUser(Long currentUserId);

    public List<Email> findAllById(Long id);

    public Optional<Email> findById(Long id);

    public Email save(Email entityToSave);

    public void delete(Email id);

    

}