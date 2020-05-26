/**
 * interface to make method headers for the implementations to be used in the 
 * EmailService java class
 * 
 * @author gIlias
 */

package george.javawebemail.Service;

import java.util.List;

import george.javawebemail.Entities.Email;

public interface IEmailService {

    public List<Email> findAllEmails(Long emailId);

    public Email save(Email entityToSave);

    public void delete(Email entityToDelete);

    public Email findById(Long emailId);

    public Email merge(Email entityToMerge);
}