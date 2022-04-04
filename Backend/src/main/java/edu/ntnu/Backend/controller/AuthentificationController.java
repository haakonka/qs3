package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DTO.LoginDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class AuthentificationController {
    private final UserService userService;
    private final AutenticationService autenticationService;

    public AuthentificationController(UserService userService, AutenticationService autenticationService) {
        this.userService = userService;
        this.autenticationService = autenticationService;
    }

    @PostMapping("/login/authentication")
    public ResponseEntity<String> loggingIn(@RequestBody LoginDTO loginDTO)
            throws NoSuchAlgorithmException, ServletException, IOException, MessagingException {
        System.out.println("in login methode");
        System.out.println(loginDTO.password);
        System.out.println(loginDTO.username);
        if (autenticationService.attemptAuthentication(loginDTO.getUsername(), loginDTO.getPassword())) {
            UserDAO user = userService.findByEmail(loginDTO.getUsername());
            String token = autenticationService.successfulAuthentication(user);
            return new ResponseEntity(token, HttpStatus.OK);
        }
        return new ResponseEntity("Failed login", HttpStatus.BAD_REQUEST);
    }
}
