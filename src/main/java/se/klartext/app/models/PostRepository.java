package se.klartext.app.models;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/**
 * Created by suchuan on 2017-05-21.
 */
public interface PostRepository extends CrudRepository<Post,Long> {
    Collection<Post> findByCreatedById(Long userId);
}
