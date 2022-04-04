package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.*;
import edu.ntnu.Backend.model.DTO.InstancesOfUserInSubjectDTO;
import edu.ntnu.Backend.model.DTO.SubjectIdDTO;
import edu.ntnu.Backend.model.DTO.UniqueIdDTO;
import edu.ntnu.Backend.model.DTO.newAssignmentDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A class that functions as the main controller for the api calls related to assignments.
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
public class AssignmentController {
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;
    private final AssignmentService assignmentService;

    public AssignmentController(AutenticationService autenticationService, UserSubjectService userSubjectService,
                                AssignmentUserService assignmentUserService,
                                AssignmentIntervalService assignmentIntervalService,
                                AssignmentService assignmentService) {
        this.autenticationService = autenticationService;
        this.userSubjectService = userSubjectService;
        this.assignmentUserService = assignmentUserService;
        this.assignmentIntervalService = assignmentIntervalService;
        this.assignmentService = assignmentService;
    }

    /**
     * A method to get all assignments of a user in a specific subject
     * @param subjectIdDTO The specific format of data that is needed.
     *                     See {@link edu.ntnu.Backend.model.DTO.SubjectIdDTO SubjectIdDTO} for more information.
     * @return Returns a response entity containing the assignmentDAO objects if the user is a logged-in user,
     * and Http status forbidden if a user is not logged-in.
     */
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
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to change the status of the given assignment for a user.
     * Only accessible to users with role studass or admin, not students.
     * @param assignmentUserDTO The specific format of data that is needed.
     *                          See {@link edu.ntnu.Backend.model.DTO.UniqueIdDTO UniqueIdDTO} for more information.
     * @return Returns a response entity containing information regarding whether the assignment was changed or not.
     * Returns Http status forbidden if a user is not logged-in, or not of an allowed role.
     */
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

    /**
     * A method to get all assignments for a user given the token of another user.
     * @param instances The specific format of data that is needed.
     *                  See {@link edu.ntnu.Backend.model.DTO.InstancesOfUserInSubjectDTO InstancesOfUserInSubjectDTO}
     *                  for more information.
     * @return Returns all the assignments for the specified user, or Http status forbidden if a user is not logged-in.
     */
    @PostMapping("user/assignments/instance")
    public ResponseEntity getAllAssignmentsForAUserWithDifferentId(@RequestBody InstancesOfUserInSubjectDTO instances) {
        if (autenticationService.checkIfAuthorized(instances.getToken(), 0)) {
            return ResponseEntity.ok().body(assignmentUserService.findBySubjectCodeAndYearAndUserID(
                    instances.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(instances.getSchoolYear()), Integer.valueOf(instances.getUserId())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method for an admin to add an assignment to a subject.
     * @param newAssignmentDTO The specific format of data that is needed.
     *                         See {@link edu.ntnu.Backend.model.DTO.newAssignmentDTO newAssignmentDTO} for more information.
     * @return Returns nothing if the method works or Http status forbidden if a user is not logged-in or not of role admin.
     */
    @PostMapping("admin/addAsignment")
    public ResponseEntity addAssignment(@RequestBody newAssignmentDTO newAssignmentDTO) {
        if (autenticationService.checkIfAuthorized(newAssignmentDTO.getToken(), 2)) {
            AssignmentDAO assignmentDAO = new AssignmentDAO(newAssignmentDTO.getAssignmentNumber(),
                    newAssignmentDTO.getSubjectCode(), Integer.parseInt(newAssignmentDTO.getSchoolYear()));
            assignmentService.saveAssignment(assignmentDAO);
            System.out.println("added the assignment : " + assignmentDAO.getSubjectCode() + " nr: "
                    + assignmentDAO.getAssignmentNumber());
            AssignmentIntervalDAO assignmentIntervalDAO = new AssignmentIntervalDAO(newAssignmentDTO.getSubjectCode(),
                    Integer.parseInt(newAssignmentDTO.getSchoolYear()), newAssignmentDTO.getAssignmentNumber(),
                    newAssignmentDTO.getIntervalStart(), newAssignmentDTO.getIntervalEnd(),
                    newAssignmentDTO.getMinAssignments());

            assignmentIntervalService.saveAssginmentInterval(assignmentIntervalDAO);

            List<UserSubjectDAO> usersInSub = userSubjectService.findAllUsersInSubject(
                    Integer.parseInt(newAssignmentDTO.getSchoolYear()), newAssignmentDTO.getSubjectCode());

            for (int i = 0; i < usersInSub.size(); i++) {
                AssignmentUserDAO assignmentUserDAO = new AssignmentUserDAO(
                        usersInSub.get(i).getUserId(), newAssignmentDTO.getSubjectCode(),
                        Integer.parseInt(newAssignmentDTO.getSchoolYear()),
                        newAssignmentDTO.getAssignmentNumber(), 0);
                assignmentUserService.addAssignmentUser(assignmentUserDAO);
                System.out.println("added assignment for user");
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to get all assignment intervals in the specified subject.
     * @param subjectIdDTO The specific format of data that is needed.
     *                     See {@link edu.ntnu.Backend.model.DTO.SubjectIdDTO SubjectIdDTO} for more information.
     * @return Returns a response entity containing the assignmentIntervalDAO objects in its body. This method may also
     * return Http status forbidden if a user is not logged-in.
     */
    @PostMapping("user/assignments/interval")
    public ResponseEntity getAssignmentIntervalForASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO) {
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
