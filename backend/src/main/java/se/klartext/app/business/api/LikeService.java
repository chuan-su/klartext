package se.klartext.app.business.api;

import se.klartext.app.model.Example;
import se.klartext.app.model.Like;
import se.klartext.app.model.User;

public interface LikeService {

    Like addIfNotPresent(Example example, User user);

    void deleteIfPresent(Example example, User user);
}
