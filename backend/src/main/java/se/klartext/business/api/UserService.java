package se.klartext.business.api;

import se.klartext.domain.model.user.AuthToken;
import se.klartext.domain.model.user.User;

import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<AuthToken> auth(User user);

    Optional<User> profile(Long userId);
}
