package fi.teaching.spring.controllers;

import fi.teaching.spring.services.UserRegistrationFailureException;
import fi.teaching.spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by kjanowsk on 2015-10-03.
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getIndex() {
        return "index";
    }

    @RequestMapping("/logged")
    public String getLogged() {
        return "logged";
    }

    @RequestMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password1, @RequestParam String password2) throws UserRegistrationFailureException {
        userService.registerUser(email, password1, password2);
        return "login";
     }

    @RequestMapping("/login")
    public String register(@RequestParam String email, @RequestParam String password) throws UserRegistrationFailureException {
        userService.loginUser(email, password);
        return "logged";
    }

}
