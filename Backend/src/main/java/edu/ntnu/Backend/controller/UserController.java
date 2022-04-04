package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.NewUserDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.model.DTO.UserbyIdDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;
    private final SubjectService subjectService;
    private final ParticipantInQueService participantInQueService;
    private final AssignmentService assignmentService;
    private final EmailService emailService;

    public UserController(UserService userService, AutenticationService autenticationService,
                          UserSubjectService userSubjectService, AssignmentUserService assignmentUserService,
                          AssignmentIntervalService assignmentIntervalService, SubjectService subjectService,
                          ParticipantInQueService participantInQueService, AssignmentService assignmentService,
                          EmailService emailService) {
        this.userService = userService;
        this.autenticationService = autenticationService;
        this.userSubjectService = userSubjectService;
        this.assignmentUserService = assignmentUserService;
        this.assignmentIntervalService = assignmentIntervalService;
        this.subjectService = subjectService;
        this.participantInQueService = participantInQueService;
        this.assignmentService = assignmentService;
        this.emailService = emailService;
    }

    // remove before final submit
    @PostMapping("/admin/users")
    public ResponseEntity test(@RequestBody TokenDTO token) {
        System.out.println("Tryng to acess all users");
        System.out.println("token:" + token.getToken());
        if (autenticationService.checkIfAuthorized(token.getToken(), 2)) {
            return ResponseEntity.ok().body(userService.findAll());
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    // Genreate user from name, password and emil.

    @PostMapping("/admin/addUserToSubject")
    public ResponseEntity addNewUserFromFile(@RequestBody NewUserDTO newUserDTO) {

        System.out.println("Token:" + newUserDTO.getToken());
        System.out.println("users:" + newUserDTO.getEmail());
        System.out.println("Subject:" + newUserDTO.getSubjectCode());
        if (autenticationService.checkIfAuthorized(newUserDTO.getToken(), 2)) {
            if (userService.findByEmail(newUserDTO.getEmail()) == null) {
                userService.saveNewUser(newUserDTO);
                // reformat to UserSubjectDAO
                System.out.println("adding new user to a subject: " + newUserDTO.getEmail() + " in: "
                        + newUserDTO.getSubjectCode());
                UserSubjectDAO userSubjectDAO = new UserSubjectDAO(
                        newUserDTO.getSubjectYear(), newUserDTO.getSubjectCode(),
                        userService.findByEmail(newUserDTO.getEmail()).getId(), 0);
                userSubjectService.saveSubjectUser(userSubjectDAO);
                return ResponseEntity.ok().body(null);

            } else {
                UserSubjectDAO userSubjectDAO = new UserSubjectDAO(
                        newUserDTO.getSubjectYear(), newUserDTO.getSubjectCode(),
                        userService.findByEmail(newUserDTO.getEmail()).getId(), 0);
                userSubjectService.saveSubjectUser(userSubjectDAO);
                return ResponseEntity.ok().body(null);
            }

        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/getUser")
    public ResponseEntity getStudentByStudentID(@RequestBody UserbyIdDTO userbyIdDTO) {

        if (autenticationService.checkIfAuthorized(userbyIdDTO.getToken(), 0)) {
            return ResponseEntity.ok().body(userService.findNameByUserID(userbyIdDTO.getUserID()));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }
}
