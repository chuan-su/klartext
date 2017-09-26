package se.klartext.app.data.api.jpa;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.model.Like;

import java.util.Optional;

public interface LikeRepository extends BaseRepository<Like> {

    Optional<Like> findByUserIdAndPostId(Long userId,Long postId);

    Page<Like> findByPostId(Long postId, Pageable pageable);

    Long countByPostId(Long postId);

    Long countByUserId(Long userId);

}
