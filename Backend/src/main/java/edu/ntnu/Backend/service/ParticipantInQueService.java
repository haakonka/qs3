package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.ParticipantInQueDAO;
import edu.ntnu.Backend.repository.ParticipantInQueRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ParticipantInQueService {
    private final ParticipantInQueRepository participantInQueRepository;

    public ParticipantInQueService(ParticipantInQueRepository participantInQueRepository) {
        this.participantInQueRepository = participantInQueRepository;
    }

    public List<ParticipantInQueDAO> findAllParticipantsInAQue(String subjectCode, int schoolYear) {
        System.out.println("Finding all participants in the que for: " + subjectCode + " at year: " + schoolYear);
        List<ParticipantInQueDAO> participantInQueDAOS = participantInQueRepository.findAllBySubjectCodeAndSchoolYear(subjectCode, schoolYear);
        participantInQueDAOS
                .sort(
                        (ParticipantInQueDAO p1, ParticipantInQueDAO p2) -> {
                            if(p1.getJoinedQue().after(p2.getJoinedQue())){
                                return -1;
                            }else return 1;
                        });

        return participantInQueRepository.findAllBySubjectCodeAndSchoolYear(subjectCode, schoolYear);
    }

    public long deleteParticipantInQue(int participantInQueId) {
        if(participantInQueId > 0) {
            System.out.println("DELETING PARTICIPANT IN THE QUE AT ID: " + participantInQueId);
            long deleteRecords = participantInQueRepository.deleteParticipantInQueDAOByParticipantInQueID(participantInQueId);
            System.out.println("Length of deleteRecords are: " + deleteRecords);
            if(deleteRecords == 1) {
                return deleteRecords;
            }
            return deleteRecords;
        }
        return 0;
    }

    public Boolean createParticipantInQue(ParticipantInQueDAO participant) {
        if(participant != null) {
            participantInQueRepository.save(participant);
            return true;
        }
        return false;
    }
}
