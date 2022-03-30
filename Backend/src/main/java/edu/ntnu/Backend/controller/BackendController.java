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

    /*ADMIN API CALLS /api/admin/**
      STUDASS API CALLS /api/studass/**
      USER API CALLS /api/user**

     */


    //remove before final submit
    @GetMapping("/admin")
    public ResponseEntity<List<UserDAO>> test() {
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
    public Object checkIfUserIsValidAndSendToken(){

        return "helloWord";

    }

}
