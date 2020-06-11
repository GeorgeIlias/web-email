package george.javawebemail.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import george.javawebemail.Entities.Receivers;

@Repository
public interface ReceiversRepository extends CrudRepository<Receivers, Long> {
    public List<Receivers> findAllById(Long id);

    public Optional<Receivers> findById(Long id);

    public Receivers save(Receivers entityToSave);

    // public Receivers merge(Receivers entityToMerge);

    public void delete(Receivers entityToDelete);

}