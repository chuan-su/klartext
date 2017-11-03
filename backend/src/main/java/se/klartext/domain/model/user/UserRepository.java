package se.klartext.domain.model.user;

import se.klartext.domain.shared.BaseRepository;

import java.util.Optional;

/**
 * Created by suchuan on 2017-05-20.
 */
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);
}
