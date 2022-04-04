package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.ParticipantInQueDAO;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.*;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

@RequestMapping("/api")
@RestController
@CrossOrigin
public class QueueController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final AssignmentUserService assignmentUserService;
    private final AssignmentIntervalService assignmentIntervalService;
    private final SubjectService subjectService;
    private final ParticipantInQueService participantInQueService;
    private final AssignmentService assignmentService;
    private final EmailService emailService;

    public QueueController(UserService userService, AutenticationService autenticationService,
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
            format.setTimeZone(TimeZone.getTimeZone("GMT+2"));
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

    @PostMapping("user/participantInQue/allInstances")
    public ResponseEntity getAllInstancesOfAUserInTheQueue(@RequestBody InstancesOfUserInSubjectDTO instances) {
        if (autenticationService.checkIfAuthorized(instances.getToken(), 0)) {
            return ResponseEntity.ok().body(participantInQueService.findAllInstancesOfAParticipantInQue(
                    instances.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(instances.getSchoolYear()),
                    Integer.valueOf(instances.getUserId())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("studass/participantInQue/status")
    public ResponseEntity changeStatusOfAParticipantInQue(@RequestBody ParticipantStatusDTO participant) {
        if (autenticationService.checkIfAuthorized(participant.getToken(), 1)) {
            participantInQueService.changeStatusOfParticipantInQueue(participant.getParticipantInQueId(),
                    participant.getStatusChange());
            return ResponseEntity.ok().body("The status of the participant was successfully changed");
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }


}
