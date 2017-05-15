package se.klartext.app.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by suchuan on 2017-05-15.
 */
@RestController
public class UserController {
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public String greeting(@RequestParam(value = "name",required = false,defaultValue = "World") String name){
        return "Hi" + name;
    }
}
