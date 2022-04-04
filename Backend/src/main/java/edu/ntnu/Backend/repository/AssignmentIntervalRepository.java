package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentIntervalDAO;
import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the assignmentinterval table in the database.
 * This class contains some premade methods and one custom-made one.
 */
@Repository
public interface AssignmentIntervalRepository extends JpaRepository<AssignmentIntervalDAO, Long> {

    List<AssignmentIntervalDAO> findAssignmentIntervalDAOBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);
}
