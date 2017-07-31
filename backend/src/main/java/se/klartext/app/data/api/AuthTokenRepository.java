package se.klartext.app.data.api;


import org.springframework.data.repository.Repository;
import se.klartext.app.model.AuthToken;

import java.util.Optional;

public interface AuthTokenRepository extends Repository<AuthToken,Long> {

    <T extends AuthToken> T save(T authToken);
    <T extends AuthToken> Optional<T> findByToken(String token);
    <T extends AuthToken> void delete(T authToken);
    <T extends AuthToken> Optional<T> findByUserId(Long userId);
}
