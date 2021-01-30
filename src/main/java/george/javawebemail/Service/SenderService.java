package george.javawebemail.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import george.javawebemail.Entities.Senders;
import george.javawebemail.repositories.SenderRepository;
import george.javawebemail.Exceptions.*;

@Service
@Transactional
public class SenderService implements ISenderService {

    @Autowired
    private SenderRepository senderRepoObject;

    @Override
    public List<Senders> findAllById(Long id) {
        return null;
    }

    @Override
    public void delete(Senders entityToDelete) throws IncorrectDatabaseResponse, NoDatabaseObject {
        try {
            senderRepoObject.delete(entityToDelete);
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;

        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();
            throw idr;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Senders save(Senders entityToSave) {
        Senders entityToReturn = null;
        try {
            entityToReturn = senderRepoObject.save(entityToSave);
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;

        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();
            throw idr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityToReturn;
    }

    @Override
    public List<Senders> findAllSendersByIdAndUserId(Long emailId, Long userId) {
        List<Senders> listToReturn = null;
        try {
            listToReturn = senderRepoObject.findAllSendersByIdAndUserId(emailId, userId);
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;
        } catch (IncorrectDatabaseResponse idr) {
            idr.printStackTrace();
            throw idr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listToReturn;
    }

}
