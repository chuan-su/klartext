package se.klartext.app.data.api;

import org.springframework.data.repository.CrudRepository;
import se.klartext.app.model.User;

/**
 * Created by suchuan on 2017-05-20.
 */
public interface UserRepository extends CrudRepository<User,Long> {

}
