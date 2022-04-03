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
}
