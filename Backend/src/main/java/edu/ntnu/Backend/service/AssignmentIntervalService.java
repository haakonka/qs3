package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.AssignmentIntervalDAO;
import edu.ntnu.Backend.repository.AssignmentIntervalRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AssignmentIntervalService {
    private final AssignmentIntervalRepository assignmentIntervalRepository;

    public AssignmentIntervalService(AssignmentIntervalRepository assignmentIntervalRepository) {
        this.assignmentIntervalRepository = assignmentIntervalRepository;
    }

    public void saveAssginmentInterval(AssignmentIntervalDAO assignmentIntervalDAO){
     assignmentIntervalRepository.save(assignmentIntervalDAO);
    }

    public List<AssignmentIntervalDAO> findBySubjectCodeAndYear(String subjectCode, int schoolYear) {
        System.out.println("finding assignment interval by subjectCode and schoolYear: " + subjectCode + " and " + schoolYear);
        if( assignmentIntervalRepository.findAssignmentIntervalDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear).isEmpty()){
            return null;
        }
        return assignmentIntervalRepository.findAssignmentIntervalDAOBySubjectCodeAndSchoolYear(subjectCode,schoolYear);
    }

}
