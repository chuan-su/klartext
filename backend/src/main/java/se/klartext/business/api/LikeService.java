package se.klartext.business.api;

import se.klartext.domain.model.post.example.Example;
import se.klartext.domain.model.post.Like.Like;
import se.klartext.domain.model.user.User;

public interface LikeService {

    Like addIfNotPresent(Example example, User user);

    void deleteIfPresent(Example example, User user);
}
