/**
 * Repository class for the cc entity
 * 
 * @author gIlias
 */

package george.javawebemail.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.CC;
import java.util.List;
import java.util.Optional;

@Repository
public interface CCRepository extends CrudRepository<CC, Long> {

    public List<CC> findAllById(Long id);

    public Optional<CC> findById(Long id);

    public CC save(CC entity);

    public void delete(CC entityToDelete);

}