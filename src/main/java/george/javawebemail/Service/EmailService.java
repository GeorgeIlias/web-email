package george.javawebemail.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;
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

    @Override
    public List<Email> findEmailsByEmailIdAndUser(Long id, User user) {
        try {
            return emailRepoObject.findAllByIdAndByEmailUserTableId(id, user.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Email> findAllByUSer(Long currentUserId) {
        try {
            return emailRepoObject.findAllByUser(currentUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Email findByIdAndByUserSent(Long id, User currentUserSent) {
        try {
            return emailRepoObject.findByIdAndByEmailUserTableId(id, currentUserSent.getId()).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}