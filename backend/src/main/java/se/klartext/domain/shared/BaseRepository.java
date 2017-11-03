package se.klartext.domain.shared;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.Optional;
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends Repository<E,Long> {

    Optional<E> findOne(Long id);

    E save(E entity);

    void delete(E entity);

}
