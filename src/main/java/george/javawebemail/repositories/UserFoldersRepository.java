package george.javawebemail.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.UserFolders;
import george.javawebemail.Entities.User;
import java.util.List;

@Repository
public interface UserFoldersRepository extends CrudRepository<UserFolders, Long> {

    public List<UserFolders> findByUser(User user);

    public UserFolders save(UserFolders entityToSave);

    @Query(value = "delete from user_folders uf where uf.user_id = :user", nativeQuery = true)
    public void delete(@Param("user") User user);

    @Query(value = "delete from user_folders uf where uf.id = :id", nativeQuery = true)
    public void delete(@Param("id") Long id);

    public void delete(UserFolders entity);

}
