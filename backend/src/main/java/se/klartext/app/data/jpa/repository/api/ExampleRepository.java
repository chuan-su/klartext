package se.klartext.app.data.jpa.repository.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.data.jpa.entity.Example;

/**
 * Created by suchuan on 2017-05-21.
 */
public interface ExampleRepository extends BaseRepository<Example> {

    Page<Example> findByCreatedById(Long userId, Pageable pageable);
}
