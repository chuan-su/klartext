package se.klartext.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.klartext.app.entity.User;
import se.klartext.app.repository.UserRepository;

/**
 * Created by suchuan on 2017-05-15.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<User> all(){
        return userRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@RequestBody User user){
        return userRepository.save(user);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public User get(@PathVariable long id){
        return userRepository.findOne(id);
    }
}
