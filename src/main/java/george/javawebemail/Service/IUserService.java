package george.javawebemail.Service;

import java.util.List;
import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;

public interface IUserService {

    public List<Email> getAllEmails(Long userId);

    public User findById(Long userId);

    public User saveUser(User userToSave);

    public void deleteUser(User userEntity);

    public User findUserByUserNameAndPasswordHash(String username, String passwordHash);

    public User findUserByCookieHash(String cookieHash);
}