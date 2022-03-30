package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BackendController {
    private final UserService userService;
    private final AutenticationService autenticationService;


//remove before final submit
    @GetMapping("/test")
    public ResponseEntity<List<UserDAO>> test() {

    return ResponseEntity.ok().body(userService.findAll());
    }

    @PostMapping("/save/user")
    public ResponseEntity<UserDAO> saveUser(@RequestBody UserDAO userDAO) {
        return ResponseEntity.ok().body(userService.saveUserDAO(userDAO));
    }



    @RequestMapping("/autent")
    public Object checkIfUserIsValidAndSendToken(){

        return "helloWord";

    }

}
