package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.ParticipantInQueueDAO;
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

/**
 * The controller class for api calls related to the queue.
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
public class QueueController {
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final SubjectService subjectService;
    private final ParticipantInQueueService participantInQueueService;

    public QueueController(AutenticationService autenticationService, UserSubjectService userSubjectService,
                           SubjectService subjectService, ParticipantInQueueService participantInQueueService) {
        this.autenticationService = autenticationService;
        this.userSubjectService = userSubjectService;
        this.subjectService = subjectService;
        this.participantInQueueService = participantInQueueService;
    }

    /**
     * A method to change the status of the que in a specific subject. Only usable for users with roles of either
     * studass or admin.
     * @param subjectIdDTO The specific format of data that is needed.
     *                     See {@link edu.ntnu.Backend.model.DTO.SubjectIdDTO SubjectIdDTO} for more information.
     * @return Returns a response entity containing the status message of the method, or a http status forbidden if
     * the subject is invalid, if a user is not logged-in or not of an allowed role.
     */
    @PostMapping("/studass/queStatus")
    public ResponseEntity changeTheStatusOfTheQueueInASubject(@RequestBody SubjectIdDTO subjectIdDTO) {
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

    /**
     * A method to get all the participants within a que of a subject.
     * @param subjectIdDTO The specific format of data that is needed.
     *                     See {@link edu.ntnu.Backend.model.DTO.SubjectIdDTO SubjectIdDTO} for more information.
     * @return Returns a response entity containing the participantInQueueDAO objects,
     * or a http status forbidden the user is not logged-in.
     */
    @PostMapping("user/participantsInQue")
    public ResponseEntity getAllParticipantsInAQueue(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access all participants in a que");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\", ""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)) {
            return ResponseEntity.ok().body(participantInQueueService.findAllParticipantsInAQue(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to delete a participant from the que.
     * @param participantInQueDTO The specific format of data that is needed.
     *                            See {@link edu.ntnu.Backend.model.DTO.UniqueIdDTO UniqueIdDTO} for more information.
     * @return Returns a response entity containing the status message of the request,
     * or a http status forbidden the user is not logged-in.
     */
    @PostMapping("user/participantInQue/delete")
    public ResponseEntity deleteParticipantInQueue(@RequestBody UniqueIdDTO participantInQueDTO) {
        System.out.println("Trying to delete a participant in the que");
        if (autenticationService.checkIfAuthorized(participantInQueDTO.getToken(), 0)) {
            System.out.println(participantInQueDTO.getUniqueId());
            long amountDeleted = participantInQueueService
                    .deleteParticipantInQue(Integer.parseInt(participantInQueDTO.getUniqueId()));
            if (amountDeleted == 1) {
                return ResponseEntity.ok().body("The participant was successfully deleted");
            } else if (amountDeleted == 0) {
                return ResponseEntity.ok().body("There was no participants with the id provided");
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to create a participantInQueueDAO object with some given data.
     * This method adds the object to the que within the given subject.
     * @param participantInQueueDTO The specific format of data that is needed.
     *                              See {@link edu.ntnu.Backend.model.DTO.ParticipantInQueueDTO ParticipantInQueueDTO}
     *                              for more information.
     * @return Returns a response entity containing the status message of the request,
     * or a http status forbidden the user is not logged-in.
     */
    @PostMapping("user/participantInQue/create")
    public ResponseEntity createParticipantInQueue(@RequestBody ParticipantInQueueDTO participantInQueueDTO) {
        if (autenticationService.checkIfAuthorized(participantInQueueDTO.getToken(), 0)) {
            UserDAO user = autenticationService.getUserFromJWT(participantInQueueDTO.getToken());
            Date date = new Date(Long.parseLong(participantInQueueDTO.getJoinedQue()));
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            String formatted = format.format(date);
            Timestamp timeStamp = Timestamp.valueOf(formatted);
            ParticipantInQueueDAO participant = new ParticipantInQueueDAO(
                    user.getId(), participantInQueueDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(participantInQueueDTO.getSchoolYear()),
                    Integer.valueOf(participantInQueueDTO.getAssignmentNumber()),
                    timeStamp);
            if (participantInQueueService.createParticipantInQue(participant)) {
                return ResponseEntity.ok().body("The participant was made");
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to get all the instances of a user within the queue.
     * @param instances The specific format of data that is needed.
     *                  See {@link edu.ntnu.Backend.model.DTO.InstancesOfUserInSubjectDTO InstancesOfUserInSubjectDTO}
     *                  for more information.
     * @return Returns a response entity containing all the participantInQueue object that have the given user id and
     * subject id. This method may also return a http status forbidden if the user is not logged-in.
     */
    @PostMapping("user/participantInQue/allInstances")
    public ResponseEntity getAllInstancesOfAUserInTheQueue(@RequestBody InstancesOfUserInSubjectDTO instances) {
        if (autenticationService.checkIfAuthorized(instances.getToken(), 0)) {
            return ResponseEntity.ok().body(participantInQueueService.findAllInstancesOfAParticipantInQue(
                    instances.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(instances.getSchoolYear()),
                    Integer.valueOf(instances.getUserId())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to change the status of a given participant in the queue. Only usable for users of roles studass or admin.
     * @param participant The specific format of data that is needed.
     *                    See {@link edu.ntnu.Backend.model.DTO.ParticipantStatusDTO ParticipantStatusDTO}
     *                    for more information.
     * @return Returns a response entity containing either the status of the request or a http status forbidden if the
     * user is not logged-in or not of an allowed role.
     */
    @PostMapping("studass/participantInQue/status")
    public ResponseEntity changeStatusOfAParticipantInQueue(@RequestBody ParticipantStatusDTO participant) {
        if (autenticationService.checkIfAuthorized(participant.getToken(), 1)) {
            participantInQueueService.changeStatusOfParticipantInQueue(participant.getParticipantInQueId(),
                    participant.getStatusChange());
            return ResponseEntity.ok().body("The status of the participant was successfully changed");
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }
}
