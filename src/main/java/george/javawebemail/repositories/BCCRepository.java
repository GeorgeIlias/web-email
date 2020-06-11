package george.javawebemail.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import george.javawebemail.Entities.BCC;
import java.util.List;
import java.util.Optional;

@Repository
public interface BCCRepository extends CrudRepository<BCC, Long> {

    public List<BCC> findAllById(Long id);

    public BCC save(BCC entity);

    public Optional<BCC> findById(Long id);

    public void delete(BCC entityToDelete);

}