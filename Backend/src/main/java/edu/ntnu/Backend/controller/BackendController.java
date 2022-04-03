package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.*;
import edu.ntnu.Backend.model.DTO.*;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

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
    public ResponseEntity<String> loggingIn(@RequestBody LoginDTO loginDTO)
            throws NoSuchAlgorithmException, ServletException, IOException {
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

    @PostMapping("/admin/addUserFromFile")
    public ResponseEntity addNewUserFromFile(@RequestBody NewUserDTO newUserDTO) {

        System.out.println("Token:" + newUserDTO.getToken());
        System.out.println("users:\n" + newUserDTO.getEmail());
        if (autenticationService.checkIfAuthorized(newUserDTO.getToken(), 2)) {
            userService.saveNewUser(newUserDTO);
            return ResponseEntity.ok().body(null);
        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
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
                    assignmentUserService.changeStatusOfAssignment(Integer.valueOf(assignmentUserDTO.getUniqueId()));
                    return ResponseEntity.ok().body("The status was changed");
                }
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

    @PostMapping("/studass/queStatus")
    public ResponseEntity changeTheStatusOfTheQueInASubject(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to change the que status of a specific subject");
        System.out.println("Token:" + subjectIdDTO.getToken());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 1)) {
            UserDAO user = autenticationService.getUserFromJWT(subjectIdDTO.getToken());
            List<UserSubjectDAO> userSubjects = userSubjectService.findByUserId(user.getId());
            AtomicReference<UserSubjectDAO> subject = null;
            for (int i = 0; i < userSubjects.size(); i++) {
                if ((userSubjects.get(i).getSubjectCode()
                        .contentEquals(subjectIdDTO.getSubjectCode().replace("\\", "")))
                        && (userSubjects.get(i).getSchoolYear() == Integer.valueOf(subjectIdDTO.getSchoolYear()))) {
                    subject.set(userSubjects.get(i));
                }
            }
            if (subject.get().getStatusOfUser() >= 1) {
                System.out.println("The que status will now change");
                if (subjectService.changeStatusOfQue(subjectIdDTO.getSubjectCode().replace("\\", ""),
                        Integer.valueOf(subjectIdDTO.getSchoolYear()))) {
                    return ResponseEntity.ok().body("The que status was changed successfully");
                }
                return new ResponseEntity("not a valid subject", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/participantsInQue")
    public ResponseEntity getAllParticipantsInAQue(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access all participants in a que");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\", ""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)) {
            return ResponseEntity.ok().body(participantInQueService.findAllParticipantsInAQue(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/participantInQue/delete")
    public ResponseEntity deleteParticipantInQue(@RequestBody UniqueIdDTO participantInQueDTO) {
        System.out.println("Trying to delete a participant in the que");
        if (autenticationService.checkIfAuthorized(participantInQueDTO.getToken(), 0)) {
            System.out.println(participantInQueDTO.getUniqueId());
            long amountDeleted = participantInQueService
                    .deleteParticipantInQue(Integer.parseInt(participantInQueDTO.getUniqueId()));
            if (amountDeleted == 1) {
                return ResponseEntity.ok().body("The participant was successfully deleted");
            } else if (amountDeleted == 0) {
                return ResponseEntity.ok().body("There was no participants with the id provided");
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/participantInQue/create")
    public ResponseEntity createParticipantInQue(@RequestBody ParticipantInQueDTO participantInQueDTO) {
        if (autenticationService.checkIfAuthorized(participantInQueDTO.getToken(), 0)) {
            UserDAO user = autenticationService.getUserFromJWT(participantInQueDTO.getToken());
            Date date = new Date(Long.parseLong(participantInQueDTO.getJoinedQue()));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);
            Timestamp timeStamp = Timestamp.valueOf(formatted);
            ParticipantInQueDAO participant = new ParticipantInQueDAO(
                    user.getId(), participantInQueDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(participantInQueDTO.getSchoolYear()),
                    Integer.valueOf(participantInQueDTO.getAssignmentNumber()),
                    timeStamp);
            if (participantInQueService.createParticipantInQue(participant)) {
                return ResponseEntity.ok().body("The participant was made");
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
    }<<<<<<<HEAD

    =======>>>>>>>61ef 0 aeba6b54b57d9309efe788483bb82e778f6
}
