package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import edu.ntnu.Backend.repository.AssignmentUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AssignmentUserService {
    private final AssignmentUserRepository assignmentUserRepository;

    public AssignmentUserService(AssignmentUserRepository assignmentUserRepository) {
        this.assignmentUserRepository = assignmentUserRepository;
    }

    public List<AssignmentUserDAO> findBySubjectCodeAndYearAndUserID(String subjectCode, int schoolYear, int userID) {
        System.out.println("finding assignmentUser by userID, subjectCode and schoolYear: " + userID + ", " + subjectCode + " and " + schoolYear);
        return assignmentUserRepository.findAssignmentUserDAOBySubjectCodeAndSchoolYearAndUserId(subjectCode,schoolYear,userID);
    }

    public List<AssignmentUserDAO> findAllSubjectsByUserID(int userID) {
        System.out.println("FINDING ALL SUBJECTS FOR USER ID: " + userID);
        return assignmentUserRepository.findAssignmentUserDAOByUserId(userID);
    }
}
