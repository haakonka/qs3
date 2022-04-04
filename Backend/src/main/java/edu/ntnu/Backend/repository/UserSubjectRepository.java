package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the userSubject table in the database.
 * This class contains some premade methods and two custom-made ones.
 */
@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubjectDAO, Long> {

    List<UserSubjectDAO> findUserSubjectDAOByUserId(int userid);

    List<UserSubjectDAO> findUserSubjectDAOBySchoolYearAndSubjectCode(int schoolYear, String subjectCode);

    
}
