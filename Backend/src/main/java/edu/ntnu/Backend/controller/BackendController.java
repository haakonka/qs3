package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DTO.LoginDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import edu.ntnu.Backend.service.UserSubjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class BackendController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;

    public BackendController(UserService userService, UserSubjectService userSubjectService ) {
        this.userService = userService;
        this.autenticationService = new AutenticationService(userService);
        this.userSubjectService = userSubjectService;
    }

    @PostMapping("/login/authentication")
    public ResponseEntity<String> loggingIn(@RequestBody LoginDTO loginDTO) throws NoSuchAlgorithmException, ServletException, IOException {
        System.out.println("in login methode");
        System.out.println(loginDTO.password);
        System.out.println(loginDTO.username);
        if (autenticationService.attemptAuthentication(loginDTO.getUsername(), loginDTO.getPassword())) {
            UserDAO user = userService.findByEmail(loginDTO.getUsername());
            String token = autenticationService.successfulAuthentication(user);
            return new ResponseEntity(token,HttpStatus.OK);
        }
        return new ResponseEntity("Failed login", HttpStatus.BAD_REQUEST);
    }


    //remove before final submit
    @PostMapping("/admin/users")
    public ResponseEntity test(@RequestBody TokenDTO token) {
        System.out.println("Tryng to acess all users");
        System.out.println("token:" + token.getToken());
        if(autenticationService.checkIfAuthorized(token.getToken(), 2)){
            return ResponseEntity.ok().body(userService.findAll());
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);

    }


    @PostMapping("/user/subjects")
    public ResponseEntity getSubjectForUser(@RequestBody TokenDTO token){
        System.out.println("Tryng to acess all users");
        System.out.println("token:" + token.getToken());
        if(autenticationService.checkIfAuthorized(token.getToken(), 0)){
            UserDAO user = autenticationService.getUserFromJWT(token.getToken());
            System.out.println(userSubjectService.findByUserId(user.getId()).get(0).getSubjectCode());
            return ResponseEntity.ok().body(userSubjectService.findByUserId(user.getId()));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }


}
