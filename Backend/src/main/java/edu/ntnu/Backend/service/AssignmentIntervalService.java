package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.AssignmentIntervalDAO;
import edu.ntnu.Backend.repository.AssignmentIntervalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * A class meant to use the access-point made to the assignmentinterval table in the database.
 * This class uses the methods from the
 * {@link edu.ntnu.Backend.repository.AssignmentIntervalRepository AssignmentIntervalRepository}
 */
@Service
@Transactional
public class AssignmentIntervalService {
    private final AssignmentIntervalRepository assignmentIntervalRepository;

    public AssignmentIntervalService(AssignmentIntervalRepository assignmentIntervalRepository) {
        this.assignmentIntervalRepository = assignmentIntervalRepository;
    }

    /**
     * A method to save a new assignmentIntervalDAO object to the database.
     * @param assignmentIntervalDAO the assignmentIntervalDAO object you want to save.
     */
    public void saveAssignmentInterval(AssignmentIntervalDAO assignmentIntervalDAO){
     assignmentIntervalRepository.save(assignmentIntervalDAO);
    }

    /**
     * A method to find all assignmentIntervalDAO objects for a given subject code and subject year.
     * @param subjectCode the subject code to search for.
     * @param schoolYear the school year to search for.
     * @return Returns a list of all assignmentIntervalDAO objects in this specific subject.
     */
    public List<AssignmentIntervalDAO> findBySubjectCodeAndYear(String subjectCode, int schoolYear) {
        System.out.println("finding assignment interval by subjectCode and schoolYear: " + subjectCode + " and " + schoolYear);
        if( assignmentIntervalRepository.findAssignmentIntervalDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear).isEmpty()){
            return null;
        }
        return assignmentIntervalRepository.findAssignmentIntervalDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear);
    }

}
