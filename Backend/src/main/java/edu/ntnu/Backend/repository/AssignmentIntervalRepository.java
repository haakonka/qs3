package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentIntervalDAO;
import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentIntervalRepository extends JpaRepository<AssignmentIntervalDAO, Long> {

    List<AssignmentIntervalDAO> findAssignmentIntervalDAOBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);
}
