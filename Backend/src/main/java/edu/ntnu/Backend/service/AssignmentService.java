package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.AssignmentDAO;
import edu.ntnu.Backend.repository.AssignmentIntervalRepository;
import edu.ntnu.Backend.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public void saveAssignment(AssignmentDAO assignmentDAO){
        assignmentRepository.save(assignmentDAO);
    }
    public void removeAssignment(AssignmentDAO assignmentDAO){
        assignmentRepository.delete(assignmentDAO);
    }


}
