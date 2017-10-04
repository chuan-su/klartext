package se.klartext.app.business.api;

import se.klartext.app.data.jpa.entity.Example;
import se.klartext.app.data.jpa.entity.Like;
import se.klartext.app.data.jpa.entity.User;

public interface LikeService {

    Like addIfNotPresent(Example example, User user);

    void deleteIfPresent(Example example, User user);
}
