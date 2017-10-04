package se.klartext.app.data.jpa.repository.api;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.data.jpa.entity.Like;

import java.util.Optional;

public interface LikeRepository extends BaseRepository<Like> {

    Optional<Like> findByUserIdAndExampleId(Long userId,Long postId);

    Page<Like> findByExampleId(Long postId, Pageable pageable);

    Long countByExampleId(Long postId);

    Long countByUserId(Long userId);

}
