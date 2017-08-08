package se.klartext.app.data.api;


import org.springframework.data.repository.Repository;
import se.klartext.app.model.BaseEntity;

import java.util.Optional;

public interface BaseRepository<E extends BaseEntity> extends Repository<E,Long> {

    Optional<E> findOne(Long id);

    E save(E entity);

    void delete(E entity);

}
