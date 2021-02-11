package george.javawebemail.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.UserFolders;
import george.javawebemail.Entities.User;
import java.util.List;



@Repository
public interface UserFoldersRepository extends CrudRepository<UserFolders,Long>{
    

    @Query
    public List<UserFolders> findByUser(User user);


    @Query
    public UserFolders save(UserFolders entityToSave);


    @Query
    public Boolean delete(User user);

    

    
}
