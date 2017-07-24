package se.klartext.app.data.api;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import se.klartext.app.model.Post;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by suchuan on 2017-05-21.
 */
public interface PostRepository extends Repository<Post,Long> {

    Stream<Post> findByCreatedById(Long userId,Pageable pageable);

    Optional<Post> findOne(Long id);

    <T extends Post> T save(T post);
}
