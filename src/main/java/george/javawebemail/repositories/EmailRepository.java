/**
 * repository class fort the Email entity
 * 
 * @author gIlias
 */
package george.javawebemail.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.Email;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {

    public List<Email> findAllById(Long id);

    public Optional<Email> findById(Long id);

    public Email save(Email entityToSave);

    public void delete(Email id);

}