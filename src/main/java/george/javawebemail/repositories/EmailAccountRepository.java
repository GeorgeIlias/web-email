package george.javawebemail.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.EmailAccount;

@Repository
public interface EmailAccountRepository extends CrudRepository<EmailAccount, Long> {

    public Optional<EmailAccount> findById(Long id);

    public EmailAccount save(EmailAccount entityToSave);

    public void delete(EmailAccount entityToDelete);

    @Query(value = "select * from emailAccounts a where a.user_email_accounts_id = ?2 and a.id = ?1", nativeQuery = true)
    public Optional<EmailAccount> findByUserEmailAccountsAndId(Long emailId, Long userId);
}