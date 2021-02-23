package george.javawebemail.Service;

import java.util.List;

import george.javawebemail.Entities.User;
import george.javawebemail.Entities.UserFolders;

/**
 * interface to give the implementation guide lines on what the methods needed
 * are
 * 
 * @author gIlias
 */
public interface IUserFoldersService {

    public List<UserFolders> findByUser(User user);

    public UserFolders save(UserFolders entityToSave);

    public Boolean delete(User userToDelete);

    public void delete(Long id);

}