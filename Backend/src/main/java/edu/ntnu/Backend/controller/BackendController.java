package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DTO.AssignmentUserDTO;
import edu.ntnu.Backend.model.DTO.LoginDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;

    public BackendController(UserService userService, UserSubjectService userSubjectService,
                             AssignmentUserService assignmentUserService, AssignmentIntervalService assignmentIntervalService) {
        this.userService = userService;
        this.autenticationService = new AutenticationService(userService);
        this.userSubjectService = userSubjectService;
        this.assignmentUserService = assignmentUserService;
        this.assignmentIntervalService = assignmentIntervalService;
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

    @PostMapping("user/assignments")
    public ResponseEntity getAssignmentsForUserInASpecificSubject(@RequestBody AssignmentUserDTO assignmentUserDTO){
        System.out.println("Trying to access all assignments within a specific subject for a user");
        System.out.println("Token:" + assignmentUserDTO.getToken());
        System.out.println("Subject code:" + assignmentUserDTO.getSubjectCode().replace("\\",""));
        System.out.println("School year:" + assignmentUserDTO.getSchoolYear());
        if(autenticationService.checkIfAuthorized(assignmentUserDTO.getToken(), 0)){
            UserDAO user = autenticationService.getUserFromJWT(assignmentUserDTO.getToken());
            System.out.println(assignmentUserService.findBySubjectCodeAndYearAndUserID(assignmentUserDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(assignmentUserDTO.getSchoolYear()), user.getId()).get(0).getStatus());
            return ResponseEntity.ok().body(assignmentUserService.findBySubjectCodeAndYearAndUserID(
                    assignmentUserDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(assignmentUserDTO.getSchoolYear()), user.getId()));
            //return ResponseEntity.ok().body(assignmentUserService.findAllSubjectsByUserID(user.getId()));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/assignments/interval")
    public ResponseEntity getAssignmentIntervallForASpecificSubject(@RequestBody AssignmentUserDTO assignmentUserDTO){
        System.out.println("Trying to access all assignments intervals within a specific subject");
        System.out.println("Token:" + assignmentUserDTO.getToken());
        System.out.println("Subject code:" + assignmentUserDTO.getSubjectCode().replace("\\",""));
        System.out.println("School year:" + assignmentUserDTO.getSchoolYear());
        if(autenticationService.checkIfAuthorized(assignmentUserDTO.getToken(), 0)){
            System.out.println("Min assignments are: " +assignmentIntervalService.findBySubjectCodeAndYear(
                    assignmentUserDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(assignmentUserDTO.getSchoolYear())).get(0).getMinAssignments());
            return ResponseEntity.ok().body(assignmentIntervalService.findBySubjectCodeAndYear(
                    assignmentUserDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(assignmentUserDTO.getSchoolYear())));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }
}
