/**
 * Repository class to merge the entity with it's equivalent in the database
 * 
 * @author gIlias
 */

package george.javawebemail.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // public List<User> findAllById(Long id);

    @Query(value = "select * from User u where u.id = ?1 ", nativeQuery = true)
    public Optional<User> findbyEmbdeedIdId(Long id);

    public Optional<User> findById(Long id);

    public void delete(User entityToDelete);

    // T extends Object T can be replaced with User all it would show is a warning
    // cause the save method doesn't know exactly what object it will send back
    public <S extends User> S save(S userToSave);

    // @Query("select us from users us where us.user_name = :username and
    // us.password_hash = :passwordHash")
    @Query(value = "select * from users us where us.user_name = ?1 and us.password_hash = ?2", nativeQuery = true)
    public Optional<User> findUserByUserNameAndPasswordHash(String username, String passwordHash);

}