package se.klartext.domain.model.user;


import se.klartext.domain.shared.BaseRepository;

import java.util.Optional;

public interface AuthTokenRepository extends BaseRepository<AuthToken> {

    Optional<AuthToken> findByToken(String token);

    Optional<AuthToken> findByUserId(Long userId);
}
