package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.ParticipantInQueueDAO;
import edu.ntnu.Backend.repository.ParticipantInQueRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * A class meant to use the access-point made to the participantinque table in the database.
 * This class uses the methods from the
 * {@link edu.ntnu.Backend.repository.ParticipantInQueRepository ParticipantInQueRepository}
 */
@Service
@Transactional
public class ParticipantInQueueService {
    private final ParticipantInQueRepository participantInQueRepository;

    public ParticipantInQueueService(ParticipantInQueRepository participantInQueRepository) {
        this.participantInQueRepository = participantInQueRepository;
    }

    /**
     * A method to find all participants in a subjects que.
     * @param subjectCode the subject code for the subject.
     * @param schoolYear the school year for the subject.
     * @return Returns a list of all participants in this queue.
     */
    public List<ParticipantInQueueDAO> findAllParticipantsInAQue(String subjectCode, int schoolYear) {
        System.out.println("Finding all participants in the que for: " + subjectCode + " at year: " + schoolYear);
        List<ParticipantInQueueDAO> participantInQueueDAOS = participantInQueRepository.findAllBySubjectCodeAndSchoolYear(subjectCode, schoolYear);
        participantInQueueDAOS
                .sort(
                        (ParticipantInQueueDAO p1, ParticipantInQueueDAO p2) -> {
                            if(p1.getJoinedQue().after(p2.getJoinedQue())){
                                return -1;
                            }else return 1;
                        });

        return participantInQueRepository.findAllBySubjectCodeAndSchoolYear(subjectCode, schoolYear);
    }

    /**
     * A method to find all instances of a user within a specific queue.
     * @param subjectCode the subject code for the subject.
     * @param schoolYear the school year for the subject.
     * @param userId the user to search for.
     * @return Returns a list of all instances of the given user within this queue.
     */
    public List<ParticipantInQueueDAO> findAllInstancesOfAParticipantInQue(String subjectCode, int schoolYear, int userId) {
        System.out.println(String.format("Finding all instances of a participant in subject %s at year %s and with user id %s"
                ,subjectCode,schoolYear,userId));
        List<ParticipantInQueueDAO> allInstances = participantInQueRepository
                .findParticipantInQueDAOBySubjectCodeAndSchoolYearAndUserID(subjectCode, schoolYear, userId);
        allInstances.sort((ParticipantInQueueDAO p1, ParticipantInQueueDAO p2) -> {
            if(p1.getJoinedQue().after(p2.getJoinedQue())){
                return -1;
            }else return 1;
        });
        return participantInQueRepository.findParticipantInQueDAOBySubjectCodeAndSchoolYearAndUserID(subjectCode, schoolYear, userId);
    }

    /**
     * A method to change the status of a participant in a queue.
     * @param participantInQueId the unique id of the participant in queue object from the database.
     * @param statusChange the status to be change to. Can range from 0, 1 or 2.
     */
    public void changeStatusOfParticipantInQueue(int participantInQueId, int statusChange) {
        ParticipantInQueueDAO participant = participantInQueRepository.findParticipantInQueDAOByParticipantInQueID(participantInQueId);
        participant.setStatus(statusChange);
        participantInQueRepository.save(participant);
        System.out.println("The status of the participant has been changed");
    }

    /**
     * A method to remove a participant from the queue.
     * @param participantInQueId the unique id of the participant in queue object from the database.
     * @return Returns the amount of users deleted. This should be 1, or 0 if no such id exists within the database.
     * May also return -1 if the participant id is invalid.
     */
    public long deleteParticipantInQue(int participantInQueId) {
        if(participantInQueId > 0) {
            System.out.println("DELETING PARTICIPANT IN THE QUE AT ID: " + participantInQueId);
            long deleteRecords = participantInQueRepository.deleteParticipantInQueDAOByParticipantInQueID(participantInQueId);
            System.out.println("Length of deleteRecords are: " + deleteRecords);
            return deleteRecords;
        }
        return -1;
    }

    /**
     * A method to create a participantInQue object in the database.
     * @param participant the participantInQueueDAO object to be saved to the database.
     * @return returns true if the participant object is not null and else returns false.
     */
    public Boolean createParticipantInQue(ParticipantInQueueDAO participant) {
        if(participant != null) {
            participantInQueRepository.save(participant);
            return true;
        }
        return false;
    }
}
