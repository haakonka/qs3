package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.SubjectIdDTO;
import edu.ntnu.Backend.model.DTO.LoginDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class BackendController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;
    private final SubjectService subjectService;
    private final ParticipantInQueService participantInQueService;

    public BackendController(UserService userService, UserSubjectService userSubjectService,
                             AssignmentUserService assignmentUserService, AssignmentIntervalService assignmentIntervalService,
                             SubjectService subjectService, ParticipantInQueService participantInQueService) {
        this.userService = userService;
        this.autenticationService = new AutenticationService(userService);
        this.userSubjectService = userSubjectService;
        this.assignmentUserService = assignmentUserService;
        this.assignmentIntervalService = assignmentIntervalService;
        this.subjectService = subjectService;
        this.participantInQueService = participantInQueService;
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
    public ResponseEntity getSubjectsForUser(@RequestBody TokenDTO token){
        System.out.println("Tryng to acess all users");
        System.out.println("token:" + token.getToken());
        if(autenticationService.checkIfAuthorized(token.getToken(), 0)){
            UserDAO user = autenticationService.getUserFromJWT(token.getToken());
            System.out.println(userSubjectService.findByUserId(user.getId()).get(0).getSubjectCode());
            //got list of subject that the user has in the form of subjectuser

            List<UserSubjectDAO> userSubjects = userSubjectService.findByUserId(user.getId());
            List<SubjectDAO> subjects = new LinkedList<>();
            for(int i = 0; i < userSubjects.size(); i++) {
                subjects.add(subjectService.findSubjectBySubjectId(
                        userSubjects.get(i).getSubjectCode(),userSubjects.get(i).getSchoolYear()));
            }
            return ResponseEntity.ok().body(subjects);
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/assignments")
    public ResponseEntity getAssignmentsForUserInASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO){
        System.out.println("Trying to access all assignments within a specific subject for a user");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\",""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if(autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)){
            UserDAO user = autenticationService.getUserFromJWT(subjectIdDTO.getToken());
            System.out.println(assignmentUserService.findBySubjectCodeAndYearAndUserID(subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear()), user.getId()).get(0).getStatus());
            return ResponseEntity.ok().body(assignmentUserService.findBySubjectCodeAndYearAndUserID(
                    subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear()), user.getId()));
            //return ResponseEntity.ok().body(assignmentUserService.findAllSubjectsByUserID(user.getId()));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/assignments/interval")
    public ResponseEntity getAssignmentIntervallForASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO){
        System.out.println("Trying to access all assignments intervals within a specific subject");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\",""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if(autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)){
            System.out.println("Min assignments are: " +assignmentIntervalService.findBySubjectCodeAndYear(
                    subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())).get(0).getMinAssignments());
            return ResponseEntity.ok().body(assignmentIntervalService.findBySubjectCodeAndYear(
                    subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/subject")
    public ResponseEntity getASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access a specific subject");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\",""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if(autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)){
            System.out.println("The status of the que is: " + subjectService.findSubjectBySubjectId(
                    subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())).getStatusQue());
            return ResponseEntity.ok().body(subjectService.findSubjectBySubjectId(
                    subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/participantsInQue")
    public ResponseEntity getAllParticipantsInAQue(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access all participants in a que");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\",""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if(autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)){
            return ResponseEntity.ok().body(participantInQueService.findAllParticipantsInAQue(
                    subjectIdDTO.getSubjectCode().replace("\\",""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }

        return new ResponseEntity("not authorized",HttpStatus.FORBIDDEN);
    }
}
