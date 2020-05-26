/**
 * Repository class to merge the entity with it's equivalent in the database
 * 
 * @author gIlias
 */

package george.javawebemail.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.User;

@Repository
@Component
public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findAllById(Long id);

    public Optional<User> findById(Long id);

    public void delete(Long id);

    // T extends Object T can be replaced with User all it would show is a warning
    // cause the save method doesn't know exactly what object it will send back
    public User save(User userToSave);

    public User merge(User userToMerge);

    @Query("select u from users u where u.username = :username and u.passwordHash : passwordHash")
    public Optional<User> findUserByUserNameAndPasswordHash(String username, String passwordHash);

}