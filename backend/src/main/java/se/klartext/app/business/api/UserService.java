package se.klartext.app.business.api;

import se.klartext.app.data.jpa.entity.AuthToken;
import se.klartext.app.data.jpa.entity.User;

import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<AuthToken> auth(User user);

    Optional<User> profile(Long userId);
}
