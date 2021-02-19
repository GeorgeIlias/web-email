package george.javawebemail.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import george.javawebemail.Entities.User;
import george.javawebemail.Entities.UserFolders;
import george.javawebemail.Exceptions.NoDatabaseObject;
import george.javawebemail.repositories.UserFoldersRepository;

public class UserFoldersServiceImpl implements UserFoldersService {

    @Autowired
    private UserFoldersRepository userFoldersRepositoryEntity;

    @Override
    public List<UserFolders> findByUser(User user) {
        try {
            List<UserFolders> folderList = userFoldersRepositoryEntity.findByUser(user);
            if (folderList == null) {
                throw new NoDatabaseObject("the object was null");
            } else {
                return folderList;
            }
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public UserFolders save(UserFolders entityToSave) {
        try {

            UserFolders entityToReturn = userFoldersRepositoryEntity.save(entityToSave);

            if (entityToReturn == null) {
                throw new NoDatabaseObject("The object was not found");
            } else {
                return entityToReturn;
            }
        } catch (NoDatabaseObject ndo) {
            ndo.printStackTrace();
            throw ndo;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Boolean delete(User userToDelete) {
        boolean isBoolean = true;
        try {
            userFoldersRepositoryEntity.delete(userToDelete);
            return isBoolean;
        } catch (Exception e) {
            isBoolean = false;
            e.printStackTrace();
            return isBoolean;
        }

    }

    @Override
    public void delete(Long id) {
        try {
            userFoldersRepositoryEntity.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
