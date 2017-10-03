package se.klartext.app.data.api.jpa;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.model.Like;

import java.util.Optional;

public interface LikeRepository extends BaseRepository<Like> {

    Optional<Like> findByUserIdAndExampleId(Long userId,Long postId);

    Page<Like> findByExampleId(Long postId, Pageable pageable);

    Long countByExampleId(Long postId);

    Long countByUserId(Long userId);

}
