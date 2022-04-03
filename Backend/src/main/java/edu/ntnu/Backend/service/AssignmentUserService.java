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
        return assignmentUserRepository.findAssignmentUserDAOBySubjectCodeAndSchoolYearAndUserID(subjectCode,schoolYear,userID);
    }

    public List<AssignmentUserDAO> findAllSubjectsByUserID(int userID) {
        System.out.println("FINDING ALL SUBJECTS FOR USER ID: " + userID);
        return assignmentUserRepository.findAssignmentUserDAOByUserID(userID);
    }

    public void changeStatusOfAssignment(int assignmentUserId) {
        AssignmentUserDAO assignmentToChange = assignmentUserRepository.findAssignmentUserDAOByAssignmentUserID(assignmentUserId);
        if(assignmentToChange.getStatus() == 1) {
            assignmentToChange.setStatus(0);
        } else if (assignmentToChange.getStatus() == 0) {
            assignmentToChange.setStatus(1);
        }
        assignmentUserRepository.save(assignmentToChange);
        System.out.println("The assignment status has been changed");
    }
}
