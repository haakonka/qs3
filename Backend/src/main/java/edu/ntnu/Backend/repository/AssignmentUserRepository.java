package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentUserRepository extends JpaRepository<AssignmentUserDAO, Long> {

    List<AssignmentUserDAO> findAssignmentUserDAOBySubjectCodeAndSchoolYearAndUserID(String subjectCode, int schoolYear, int userID);

    List<AssignmentUserDAO> findAssignmentUserDAOByUserID(int userID);
}
