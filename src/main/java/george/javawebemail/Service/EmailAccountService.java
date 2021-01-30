package george.javawebemail.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import george.javawebemail.Entities.EmailAccount;
import george.javawebemail.Exceptions.IncorrectDatabaseResponse;
import george.javawebemail.Exceptions.NoDatabaseObject;
import george.javawebemail.repositories.EmailAccountRepository;

@Service
public class EmailAccountService implements IEmailAccountService {

    @Autowired
    private EmailAccountRepository emailAccountRepoObject;

    /**
     * method to get an email account by searching through the database with it's
     * supposed id
     * 
     * @param id
     * @return EmailAccount
     * @author gIlias
     */
    @Override
    public EmailAccount findEmaillAcountById(Long id) throws NoDatabaseObject {
        EmailAccount currentAccount = null;
        try {
            currentAccount = emailAccountRepoObject.findById(id).get();

            if (currentAccount == null) {
                throw new NoDatabaseObject();
            }

        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentAccount;
    }

    /**
     * method to save a gven EmailAccount object into the database
     * 
     * @return S
     * @param entityToSave
     * @author gIlias
     */
    @Override
    public EmailAccount saveEmailAccount(EmailAccount entityToSave) throws NoDatabaseObject {
        EmailAccount returningEmailAccount = null;
        try {
            returningEmailAccount = emailAccountRepoObject.save(entityToSave);

            if (returningEmailAccount == null) {
                throw new NoDatabaseObject();
            }
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returningEmailAccount;

    }

    /**
     * delete method to delete an object from the database
     * 
     * 
     * @param entityToDelete
     * @author gIlias
     * @return void
     */
    @Override
    public void deleteEmailAccount(EmailAccount entityToDelete) throws NoDatabaseObject, IncorrectDatabaseResponse {
        try {
            emailAccountRepoObject.delete(entityToDelete);
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;
        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();
            throw idr;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * finds an email account based on the email id and user id then returns it if
     * it is null then a null pointer will be thrown and caught by the controller
     * 
     * @param emailId
     * @param userId
     * @return EmailAccount
     * @author gIlias
     */
    @Override
    public EmailAccount findByUserEmailAccountAndId(Long emailId, long userId) throws NoDatabaseObject {
        EmailAccount currentEmailAccount = null;

        try {
            currentEmailAccount = emailAccountRepoObject.findByUserEmailAccountsAndId(emailId, userId).get();

            if (currentEmailAccount == null) {
                throw new NoDatabaseObject();
            }

        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return currentEmailAccount;
    }

}