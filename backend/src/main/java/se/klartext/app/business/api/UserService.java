package se.klartext.app.business.api;

import se.klartext.app.model.AuthToken;
import se.klartext.app.model.User;

import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<AuthToken> auth(User user);

    User profile(Long userId);
}
