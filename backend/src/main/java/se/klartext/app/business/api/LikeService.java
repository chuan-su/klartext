package se.klartext.app.business.api;

import se.klartext.app.model.Like;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;

public interface LikeService {

    Like addIfNotPresent(Post post, User user);

    void deleteIfPresent(Post post, User user);
}
