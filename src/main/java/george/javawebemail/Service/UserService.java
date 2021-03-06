package george.javawebemail.Service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;
import george.javawebemail.repositories.UserRepository;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepoObject;

    @Override
    public List<Email> getAllEmails(Long userId) {
        try {
            User currentUser = userRepoObject.findbyEmbdeedIdId(userId).get();
            if (currentUser == null) {
                throw new NullPointerException();
            } else {
                return currentUser.getListOfEmails();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(
                    "User has not been found in the database when trying to get all emails, please check the id again");
        }
    }

    @Override
    public User findById(Long userId) {
        try {
            User currentUser = userRepoObject.findbyEmbdeedIdId(userId).get();
            if (currentUser == null) {
                throw new NullPointerException();
            } else {
                return currentUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(
                    "Finding single user has not worked, an error with getting the user from the database has occured.  The user has not been found");
        }
    }

    @Override
    public User saveUser(User userToSave) {
        try {
            return userRepoObject.save(userToSave);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
            throw npe;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteUser(User userId) {
        try {
            userRepoObject.delete(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Will take a userName and a HASHED password then return the given user;
     */
    @Override
    public User findUserByUserNameAndPasswordHash(String username, String passwordHash) {
        try {
            User currentUser = userRepoObject.findUserByUserNameAndPasswordHash(username, passwordHash).get();
            return currentUser;
        } catch (NoSuchElementException nsee) {
            return null;
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByCookieHash(String cookieHash) {
        try {
            return userRepoObject.findByCookieHash(cookieHash).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}