package george.javawebemail.Service;

import george.javawebemail.Entities.EmailAccount;

public interface IEmailAccountService{

public EmailAccount findEmaillAcountById(Long id);

public <S extends EmailAccount> S saveEmailAccount(EmailAccount entityToSave);

public void deleteEmailAccount(EmailAccount entityToDelete);

public EmailAccount findByUserEmailAccountAndId(Long emailId, long userId);




}