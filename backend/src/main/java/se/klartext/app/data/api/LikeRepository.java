package se.klartext.app.data.api;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import se.klartext.app.model.Like;

import java.util.Optional;

public interface LikeRepository extends Repository<Like,Long> {

    Optional<Like> findByUserIdAndPostId(Long userId,Long postId);

    Page<Like> findByPostId(Long postId, Pageable pageable);

    Long countByPostId(Long postId);

    Long countByUserId(Long userId);

    Like save(Like like);

    void delete(Like like);
}
