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

/**
 * A controller for the authentication api calls of the backend.
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
public class AuthenticationController {
    private final UserService userService;
    private final AutenticationService autenticationService;

    public AuthenticationController(UserService userService, AutenticationService autenticationService) {
        this.userService = userService;
        this.autenticationService = autenticationService;
    }

    /**
     * A method to authenticate the login of a user to the queue system.
     * @param loginDTO The specific format of data that is needed.
     *                 See {@link edu.ntnu.Backend.model.DTO.LoginDTO LoginDTO} for more information.
     * @return Returns a response entity containing ether a generated token if the login is successful,
     * or a http status bad request if the login is not successful.
     * @throws NoSuchAlgorithmException
     * @throws ServletException
     * @throws IOException
     * @throws MessagingException
     */
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
