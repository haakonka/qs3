package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.QueDAO;
import edu.ntnu.Backend.repository.QueRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QueService {
    private final QueRepository queRepository;

    public QueService(QueRepository queRepository) {
        this.queRepository = queRepository;
    }

    public List<QueDAO> getAllParticipantsInAQue(String subjectCode, int schoolYear) {
        System.out.println("finding all participants by subjectCode and schoolYear: " + subjectCode + " and " + schoolYear);
        return queRepository.findQueDAOBySubjectCodeAndSchoolYear(subjectCode, schoolYear);
    }
}
