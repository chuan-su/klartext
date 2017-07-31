package se.klartext.app.data.api;

import org.springframework.data.repository.CrudRepository;
import se.klartext.app.model.User;

import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by suchuan on 2017-05-20.
 */
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
