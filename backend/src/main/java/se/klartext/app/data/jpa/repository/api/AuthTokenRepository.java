package se.klartext.app.data.jpa.repository.api;


import se.klartext.app.data.jpa.entity.AuthToken;

import java.util.Optional;

public interface AuthTokenRepository extends BaseRepository<AuthToken> {

    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByUserId(Long userId);
}
