package se.klartext.app.data.jpa.repository.api;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import se.klartext.app.data.jpa.entity.BaseEntity;

import java.util.Optional;
@NoRepositoryBean
public interface BaseRepository<E extends BaseEntity> extends Repository<E,Long> {

    Optional<E> findOne(Long id);

    E save(E entity);

    void delete(E entity);

}
