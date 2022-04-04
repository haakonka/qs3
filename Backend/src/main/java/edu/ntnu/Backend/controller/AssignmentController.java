package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.*;
import edu.ntnu.Backend.model.DTO.InstancesOfUserInSubjectDTO;
import edu.ntnu.Backend.model.DTO.SubjectIdDTO;
import edu.ntnu.Backend.model.DTO.UniqueIdDTO;
import edu.ntnu.Backend.model.DTO.newAsignemntDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class AssignmentController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;
    private final SubjectService subjectService;
    private final ParticipantInQueService participantInQueService;
    private final AssignmentService assignmentService;
    private final EmailService emailService;

    public AssignmentController(UserService userService, AutenticationService autenticationService,
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

    @PostMapping("user/assignments")
    public ResponseEntity getAssignmentsForUserInASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access all assignments within a specific subject for a user");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\", ""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)) {
            UserDAO user = autenticationService.getUserFromJWT(subjectIdDTO.getToken());
            System.out
                    .println(
                            assignmentUserService
                                    .findBySubjectCodeAndYearAndUserID(subjectIdDTO.getSubjectCode().replace("\\", ""),
                                            Integer.valueOf(subjectIdDTO.getSchoolYear()), user.getId())
                                    .get(0).getStatus());
            return ResponseEntity.ok().body(assignmentUserService.findBySubjectCodeAndYearAndUserID(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear()), user.getId()));
            // return
            // ResponseEntity.ok().body(assignmentUserService.findAllSubjectsByUserID(user.getId()));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("studass/assignment/status")
    public ResponseEntity changeAssignmentStatusOfAssignmentForUser(@RequestBody UniqueIdDTO assignmentUserDTO) {
        System.out.println("Trying to change the status of the assignment");
        System.out.println("Token:" + assignmentUserDTO.getToken());
        System.out.println("AssignmentUserId:" + assignmentUserDTO.getUniqueId());
        if (autenticationService.checkIfAuthorized(assignmentUserDTO.getToken(), 1)) {
            UserDAO user = autenticationService.getUserFromJWT(assignmentUserDTO.getToken());
            List<UserSubjectDAO> userSubjects = userSubjectService.findByUserId(user.getId());
            AssignmentUserDAO assignment = assignmentUserService.findAssignmentUserById(
                    Integer.valueOf(assignmentUserDTO.getUniqueId()));
            for (int i = 0; i < userSubjects.size(); i++) {
                if ((userSubjects.get(i).getSubjectCode().contentEquals(assignment.getSubjectCode()))
                        && (userSubjects.get(i).getSchoolYear() == assignment.getSchoolYear())) {
                    if (userSubjects.get(i).getStatusOfUser() >= 1) {
                        assignmentUserService
                                .changeStatusOfAssignment(Integer.valueOf(assignmentUserDTO.getUniqueId()));
                        return ResponseEntity.ok().body("The status was changed");
                    } else {
                        return ResponseEntity.ok().body("The user is not a studass in this subject");
                    }
                }
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/assignments/instance")
    public ResponseEntity getAllAssignmentsForAUserWithDifferentId(@RequestBody InstancesOfUserInSubjectDTO instances) {
        if (autenticationService.checkIfAuthorized(instances.getToken(), 0)) {
            return ResponseEntity.ok().body(assignmentUserService.findBySubjectCodeAndYearAndUserID(
                    instances.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(instances.getSchoolYear()), Integer.valueOf(instances.getUserId())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("admin/addAsignment")
    public ResponseEntity addAsignment(@RequestBody newAsignemntDTO newAsignemntDTO) {
        if (autenticationService.checkIfAuthorized(newAsignemntDTO.getToken(), 2)) {
            AssignmentDAO assignmentDAO = new AssignmentDAO(newAsignemntDTO.getAssignmentNumber(),
                    newAsignemntDTO.getSubjectCode(), Integer.parseInt(newAsignemntDTO.getSchoolYear()));
            assignmentService.saveAssignment(assignmentDAO);
            System.out.println("added the assignment : " + assignmentDAO.getSubjectCode() + " nr: "
                    + assignmentDAO.getAssignmentNumber());
            AssignmentIntervalDAO assignmentIntervalDAO = new AssignmentIntervalDAO(newAsignemntDTO.getSubjectCode(),
                    Integer.parseInt(newAsignemntDTO.getSchoolYear()), newAsignemntDTO.getAssignmentNumber(),
                    newAsignemntDTO.getIntervalStart(), newAsignemntDTO.getIntervalEnd(),
                    newAsignemntDTO.getMinAssignments());

            assignmentIntervalService.saveAssginmentInterval(assignmentIntervalDAO);

            List<UserSubjectDAO> usersInSub = userSubjectService.findAllUsersInSubject(
                    Integer.parseInt(newAsignemntDTO.getSchoolYear()), newAsignemntDTO.getSubjectCode());

            for (int i = 0; i < usersInSub.size(); i++) {
                AssignmentUserDAO assignmentUserDAO = new AssignmentUserDAO(
                        usersInSub.get(i).getUserId(), newAsignemntDTO.getSubjectCode(),
                        Integer.parseInt(newAsignemntDTO.getSchoolYear()),
                        newAsignemntDTO.getAssignmentNumber(), 0);
                assignmentUserService.addAssignmentUser(assignmentUserDAO);
                System.out.println("added assignment for user");
            }

        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/assignments/interval")
    public ResponseEntity getAssignmentIntervallForASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access all assignments intervals within a specific subject");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\", ""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)) {
            System.out.println("Min assignments are: " + assignmentIntervalService.findBySubjectCodeAndYear(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())).get(0).getMinAssignments());
            return ResponseEntity.ok().body(assignmentIntervalService.findBySubjectCodeAndYear(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }
}
