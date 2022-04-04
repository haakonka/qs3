package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.SubjectDTO;
import edu.ntnu.Backend.model.DTO.SubjectIdDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class SubjectController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;
    private final SubjectService subjectService;
    private final ParticipantInQueService participantInQueService;
    private final AssignmentService assignmentService;
    private final EmailService emailService;

    public SubjectController(UserService userService, AutenticationService autenticationService,
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

    @PostMapping("/user/subjects")
    public ResponseEntity getSubjectsForUser(@RequestBody TokenDTO token) {
        System.out.println("Tryng to acess all users");
        System.out.println("token:" + token.getToken());
        if (autenticationService.checkIfAuthorized(token.getToken(), 0)) {
            UserDAO user = autenticationService.getUserFromJWT(token.getToken());
            System.out.println(userSubjectService.findByUserId(user.getId()).get(0).getSubjectCode());
            // got list of subject that the user has in the form of subjectuser

            List<UserSubjectDAO> userSubjects = userSubjectService.findByUserId(user.getId());
            List<SubjectDAO> subjects = new LinkedList<>();
            for (int i = 0; i < userSubjects.size(); i++) {
                subjects.add(subjectService.findSubjectBySubjectId(
                        userSubjects.get(i).getSubjectCode(), userSubjects.get(i).getSchoolYear()));
            }
            return ResponseEntity.ok().body(subjects);
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("admin/addSubject")
    public ResponseEntity addSubject(@RequestBody SubjectDTO subjectDTO) {
        if (autenticationService.checkIfAuthorized(subjectDTO.getToken(), 2)) {
            SubjectDAO subjectDAO = new SubjectDAO(subjectDTO.getSubjectCode(),
                    Integer.parseInt(subjectDTO.getSchoolYear()), subjectDTO.getSubjectName(), 0);
            subjectService.saveNewSubject(subjectDAO);
            return ResponseEntity.ok().body("subjectAdded");
        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("admin/removeSubject")
    public ResponseEntity removeSubject(@RequestBody SubjectDTO subjectDTO) {
        if (autenticationService.checkIfAuthorized(subjectDTO.getToken(), 2)) {
            SubjectDAO subjectDAO = new SubjectDAO(subjectDTO.getSubjectCode(),
                    Integer.parseInt(subjectDTO.getSchoolYear()), subjectDTO.getSubjectName(), 0);
            subjectService.removeSubject(subjectDAO);
            return ResponseEntity.ok().body("subjectRemoved");
        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/subject")
    public ResponseEntity getASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access a specific subject");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\", ""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)) {
            System.out.println("The status of the que is: " + subjectService.findSubjectBySubjectId(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())).getStatusQue());
            return ResponseEntity.ok().body(subjectService.findSubjectBySubjectId(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }
}
