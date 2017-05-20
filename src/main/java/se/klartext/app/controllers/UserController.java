package se.klartext.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.klartext.app.models.User;
import se.klartext.app.models.UserRepository;

/**
 * Created by suchuan on 2017-05-15.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public @ResponseBody Iterable<User> greeting(){
        return userRepository.findAll();
    }

}
