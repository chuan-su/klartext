package se.klartext.app.data.api;


import org.springframework.data.repository.Repository;
import se.klartext.app.model.Like;

import java.util.Optional;

public interface LikeRepository extends Repository<Like,Long> {

    Optional<Like> findByUserIdAndPostId(Long userId,Long postId);

    Like save(Like like);
}
