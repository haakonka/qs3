package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A class meant to access the assignment table in the database.
 * This class contains some premade methods and one custom-made one.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentDAO, Long> {

    AssignmentDAO findByAssignmentNumberAndSubjectCodeAndSchoolYear(int assignmentnumber, String subjectCode, int schoolyear);
}
