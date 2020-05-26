package george.javawebemail.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import george.javawebemail.Entities.Email;
import george.javawebemail.repositories.EmailRepository;

@Service
public class EmailService implements IEmailService {

    @Autowired
    private EmailRepository emailRepoObject;

    @Override
    public List<Email> findAllEmails(Long emailId) {
        return emailRepoObject.findAllById(emailId);
    }

    /**
     * method to save a given entity in the database
     */
    @Override
    public Email save(Email entityToSave) {
        return emailRepoObject.save(entityToSave);
    }

    /**
     * method to delete a given email from the database
     * 
     * @param entityToDelete
     * @author gIlias
     */
    @Override
    public void delete(Email entityToDelete) {
        emailRepoObject.delete(entityToDelete);

    }

    /**
     * method to find emails by id
     * 
     * @param emailId
     * @author gIlias
     */
    @Override
    public Email findById(Long emailId) {
        return emailRepoObject.findById(emailId).get();
    }

    /**
     * method to find merge a given entity
     * 
     * @param entityToMerge
     * @author gIlias
     */
    public Email merge(Email entityToMerge) {
        return emailRepoObject.merge(entityToMerge);
    }

}