package se.klartext.security.api;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import se.klartext.domain.model.user.AuthToken;

import java.util.Optional;

public interface AuthenticationService {

    Optional<AuthToken> authenticate(String username, String password) throws AuthenticationException;
    Optional<UserDetails> authenticateWithToken(String token) throws AuthenticationException;
}
