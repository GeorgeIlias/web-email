package george.javawebemail.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.EmailAccount;

@Repository
@Component
public interface EmailAccountRepository extends CrudRepository<EmailAccount, Long> {

    public Optional<EmailAccount> findById(Long id);

    public EmailAccount save(EmailAccount entityToSave);

    public EmailAccount merge(EmailAccount entityToMerge);

    public void delete(EmailAccount entityToDelete);
}