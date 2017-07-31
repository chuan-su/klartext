package se.klartext.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.klartext.app.business.api.UserService;
import se.klartext.app.exception.UnauthorizedException;
import se.klartext.app.model.AuthToken;
import se.klartext.app.model.User;

import java.util.Optional;

/**
 * Created by suchuan on 2017-05-15.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public User create(@RequestBody User user){
        return userService.register(user);
    }
    @RequestMapping(value = "auth",method = RequestMethod.PUT)

    public AuthToken auth(@RequestBody User user){

        return userService.auth(user)
                .orElseThrow(() -> new UnauthorizedException("Bad Credentials"));

    }
    @RequestMapping(value="/{id}/profile",method = RequestMethod.GET)
    public User get(@PathVariable long id){
        return userService.profile(id);
    }
}
