/**
 * Class to act as an interface for the service to implement, gives a framework for how the service should look
 * 
 * @author gIlias
 */
package george.javawebemail.Service;

import java.util.List;
import george.javawebemail.Entities.Senders;

public interface ISenderService {

    public List<Senders> findAllById(Long id);

    public void delete(Senders entityToDelete);

    public Senders save(Senders entityToSave);

    //
    public List<Senders> findAllSendersByIdAndUserId(Long emailId, Long userId);

}
