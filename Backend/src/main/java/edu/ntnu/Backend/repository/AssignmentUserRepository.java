package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the assignmentUser table in the database.
 * This class contains some premade methods and three custom-made ones.
 */
@Repository
public interface AssignmentUserRepository extends JpaRepository<AssignmentUserDAO, Long> {

    List<AssignmentUserDAO> findAssignmentUserDAOBySubjectCodeAndSchoolYearAndUserID(String subjectCode, int schoolYear, int userId);

    List<AssignmentUserDAO> findAssignmentUserDAOByUserID(int userId);

    AssignmentUserDAO findAssignmentUserDAOByAssignmentUserID(int assignmentUserId);
}
