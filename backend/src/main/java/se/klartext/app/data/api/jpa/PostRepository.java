package se.klartext.app.data.api.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.model.Post;

/**
 * Created by suchuan on 2017-05-21.
 */
public interface PostRepository extends BaseRepository<Post> {

    Page<Post> findByCreatedById(Long userId, Pageable pageable);
}
