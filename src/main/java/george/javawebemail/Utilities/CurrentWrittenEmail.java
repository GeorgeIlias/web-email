/**
 * class to create and store a static email object
 * 
 * @author gIlias
 */

package george.javawebemail.Utilities;

import org.springframework.beans.factory.annotation.Autowired;

import george.javawebemail.Entities.Email;
import george.javawebemail.Service.EmailService;

public class CurrentWrittenEmail {

    @Autowired
    private EmailService emailServiceObject;

    public Email currentWrittenEmail(long id) throws Exception {
        try {
            return emailServiceObject.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("The email does not exist or cannot be found");
        }
    }

}
