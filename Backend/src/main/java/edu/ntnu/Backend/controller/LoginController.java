package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/login")
@RestController
@CrossOrigin
public class LoginController {
    private final UserService userService;
    private final AutenticationService autenticationService;


    public LoginController(UserService userService, AutenticationService autenticationService) {
        this.userService = userService;
        this.autenticationService = autenticationService;
    }


//remove before final submit
    @GetMapping("/test")
    public List<UserDAO> test() {

    return userService.findAll();
    }


    @GetMapping("/autent")
    public Object checkIfUserIsValidAndSendToken(){


        return false;

    }

}
