package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import edu.ntnu.Backend.repository.AssignmentUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * A class meant to use the access-point made to the assignmentUser table in the database.
 * This class uses the methods from the
 * {@link edu.ntnu.Backend.repository.AssignmentUserRepository AssignmentUserRepository}
 */
@Service
@Transactional
public class AssignmentUserService {
    private final AssignmentUserRepository assignmentUserRepository;

    public AssignmentUserService(AssignmentUserRepository assignmentUserRepository) {
        this.assignmentUserRepository = assignmentUserRepository;
    }

    /**
     * A method to add a assignmentUserDAO object to the database.
     * @param assignmentUserDAO The assignmentUserDAO object to be added.
     */
    public void addAssignmentUser(AssignmentUserDAO assignmentUserDAO){
        assignmentUserRepository.save(assignmentUserDAO);
    }

    /**
     * A method to find all assignments for a user given a user id, subject code and school year.
     * @param subjectCode The subject code to check for assignments in.
     * @param schoolYear The school year to check for assignments in.
     * @param userID The user you want the assignments for.
     * @return Returns the list of assignments for this user in this subject.
     */
    public List<AssignmentUserDAO> findBySubjectCodeAndYearAndUserID(String subjectCode, int schoolYear, int userID) {
        System.out.println("finding assignmentUser by userID, subjectCode and schoolYear: " + userID + ", " + subjectCode + " and " + schoolYear);
        List<AssignmentUserDAO> listOfAssignments = assignmentUserRepository.findAssignmentUserDAOBySubjectCodeAndSchoolYearAndUserID(subjectCode,schoolYear,userID);
        if(listOfAssignments.isEmpty()){
            return null;
        }
        listOfAssignments.sort(
                (AssignmentUserDAO ad1,AssignmentUserDAO ad2) -> {
                    if(ad1.getAssignmentNumber() <= ad2.getAssignmentNumber()){
                        return -1;
                    }
                    else{
                        return 1;
                    }

                } );
        return listOfAssignments;
    }
    public List<AssignmentUserDAO> findAllThatHasLargerIdThan(int value){
        return assignmentUserRepository.findAssignmentUserDAOByAssignmentUserIDIsGreaterThan(value);
    }

    /**
     * A method to find all subjects for a user.
     * @param userID the given user you want to search for.
     * @return returns all the subjects for this user.
     */
    public List<AssignmentUserDAO> findAllSubjectsByUserID(int userID) {
        System.out.println("FINDING ALL SUBJECTS FOR USER ID: " + userID);
        return assignmentUserRepository.findAssignmentUserDAOByUserID(userID);
    }

    /**
     * A method to change the status of an assignment.
     * @param assignmentUserId the unique id for the specific assignment you want to change the status of.
     */
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

    /**
     * A method to find a specific assignment.
     * @param assignmentUserId the unique id for the specific assignment you want.
     * @return Returns the specific assignment.
     */
    public AssignmentUserDAO findAssignmentUserById(int assignmentUserId) {
        System.out.println("FINDING A ASSIGNMENT FOR ID: " + assignmentUserId);
        return assignmentUserRepository.findAssignmentUserDAOByAssignmentUserID(assignmentUserId);
    }
}
