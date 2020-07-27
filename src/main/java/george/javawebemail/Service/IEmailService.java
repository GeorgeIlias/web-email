/**
 * interface to make method headers for the implementations to be used in the 
 * EmailService java class
 * 
 * @author gIlias
 */

package george.javawebemail.Service;

import java.util.List;

import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;

public interface IEmailService {

    public List<Email> findAllEmails(Long emailId);

    public Email save(Email entityToSave);

    public void delete(Email entityToDelete);

    public Email findById(Long emailId);

    public List<Email> findEmailsByEmailIdAndUser(Long id, User user);

    public List<Email> findAllByUSer(Long curerntUserId);

    public Email findByIdAndByUserSent(Long id, User currentUserSent);

}