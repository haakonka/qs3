package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentDAO, Long> {

    AssignmentDAO findByAssignmentNumberAndSubjectCodeAndSchoolYear(int assignmentnumber, String subjectCode, int schoolyear);

}
