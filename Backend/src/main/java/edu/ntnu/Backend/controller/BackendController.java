package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DTO.LoginDTO;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin

public class BackendController {
    private final UserService userService;
    private final AutenticationService autenticationService;

    public BackendController(UserService userService) {
        this.userService = userService;
        this.autenticationService = new AutenticationService(userService);
    }

    /*ADMIN API CALLS /api/admin/**
      STUDASS API CALLS /api/studass/**
      USER API CALLS /api/user**
     */


    @PostMapping("/login/authentication")
    public String loggingIn(@RequestBody LoginDTO loginDTO) throws NoSuchAlgorithmException, ServletException, IOException {
        System.out.println("in login methode");
        System.out.println(loginDTO.password);
        System.out.println(loginDTO.username);
        if (autenticationService.attemptAuthentication(loginDTO.getUsername(), loginDTO.getPassword())) {
            UserDAO user = userService.findByEmail(loginDTO.getUsername());
            String token = autenticationService.successfulAuthentication(user);
            return token;
        }

        return "failed authentication";
    }


    //remove before final submit
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDAO>> test() {
        System.out.println("Tryng to acess all users");
        return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/admin/saveuser")
    public ResponseEntity<UserDAO> saveUser(@RequestBody UserDAO userDAO) {
        return ResponseEntity.ok().body(userService.saveUserDAO(userDAO));
    }

    @GetMapping("/studass/")
    public String testStudass() {
        return "hello im a studass";
    }

    @GetMapping("/user/")
    public String testUser() {
        return "Hello im a user";
    }


    @RequestMapping("/autent")
    public Object checkIfUserIsValidAndSendToken() {

        return "helloWord";

    }

}
