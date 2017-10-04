package se.klartext.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import se.klartext.app.business.api.UserService;
import se.klartext.app.lib.exception.AccountRegistrationException;
import se.klartext.app.lib.exception.HttpBadRequestException;
import se.klartext.app.lib.exception.HttpUnauthorizedException;
import se.klartext.app.data.jpa.entity.AuthToken;
import se.klartext.app.data.jpa.entity.User;

import javax.validation.Valid;
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
    public User register(@RequestBody @Valid User userData){

        try{
            User newUser =  userService.register(userData);
            return newUser;
        }catch(AccountRegistrationException e){
            throw new HttpBadRequestException(e.getMessage());
        }
    }

    @RequestMapping(value = "auth",method = RequestMethod.PUT)
    public AuthToken auth(@RequestBody User user){
        try{
            Optional<AuthToken> authToken =  userService.auth(user);
            return authToken.get();
        }catch (AuthenticationException e){
            throw new HttpUnauthorizedException(e.getMessage());
        }
    }
    @RequestMapping(value="/{id}/profile",method = RequestMethod.GET)
    public User get(@PathVariable long id){
        return userService.profile(id).get();
    }
}
