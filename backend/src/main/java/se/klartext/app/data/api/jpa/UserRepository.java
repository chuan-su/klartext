package se.klartext.app.data.api.jpa;

import se.klartext.app.model.User;

import java.util.Optional;

/**
 * Created by suchuan on 2017-05-20.
 */
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);
}
