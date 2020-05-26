package george.javawebemail.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import george.javawebemail.Entities.Email;
import george.javawebemail.Entities.User;
import george.javawebemail.repositories.UserRepository;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepoObject;

    @Override
    public List<Email> getAllEmails(Long userId) {
        try {
            User currentUser = userRepoObject.findById(userId).get();
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
            User currentUser = userRepoObject.findById(userId).get();
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
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteUser(Long userId) {
        try {
            userRepoObject.delete(userId);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User merge(User userToMerge) {
        try {
            return userRepoObject.merge(userToMerge);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public User findUserByUserNameAndPasswordHash(String username, String passwordHash) {
        try {
            User currentUser = userRepoObject.findUserByUserNameAndPasswordHash(username, passwordHash).get();
            if (currentUser == null) {
                return null;
            } else {
                return currentUser;
            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (IllegalArgumentException iae) {
            iae.printStackTrace();
        }
        return null;
    }

}