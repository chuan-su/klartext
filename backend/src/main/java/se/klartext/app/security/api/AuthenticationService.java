package se.klartext.app.security.api;

import se.klartext.app.model.AuthToken;

import java.util.Optional;

public interface AuthenticationService {

    Optional<AuthToken> authenticate(String username, String password);

    boolean isValidToken(String token);
}
