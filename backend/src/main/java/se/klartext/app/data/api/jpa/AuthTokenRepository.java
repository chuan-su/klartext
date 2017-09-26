package se.klartext.app.data.api.jpa;


import se.klartext.app.model.AuthToken;

import java.util.Optional;

public interface AuthTokenRepository extends BaseRepository<AuthToken> {

    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByUserId(Long userId);
}
