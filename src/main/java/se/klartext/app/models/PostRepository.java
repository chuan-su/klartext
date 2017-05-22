package se.klartext.app.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by suchuan on 2017-05-21.
 */
public interface PostRepository extends Repository<Post,Long> {

    Stream<Post> findByCreatedById(Long userId);

    Optional<Post> findOne(Long id);

    <T extends Post> T save(T post);
}
