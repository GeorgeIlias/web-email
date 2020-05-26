package george.javawebemail.Service;

import java.util.List;
import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;

public interface IUserService {

    public List<Email> getAllEmails(Long userId);

    public User findById(Long userId);

    public User saveUser(User userToSave);

    public void deleteUser(Long userId);

    public User merge(User userToMerge);

    public User findUserByUserNameAndPasswordHash(String username, String passwordHash);
}