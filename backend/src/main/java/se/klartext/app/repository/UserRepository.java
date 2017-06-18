package se.klartext.app.repository;

import org.springframework.data.repository.CrudRepository;
import se.klartext.app.entity.User;

/**
 * Created by suchuan on 2017-05-20.
 */
public interface UserRepository extends CrudRepository<User,Long> {

}
