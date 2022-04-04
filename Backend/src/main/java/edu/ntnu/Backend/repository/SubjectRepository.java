package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A class meant to access the subject table in the database.
 * This class contains some premade methods and one custom-made one.
 */
@Repository
public interface SubjectRepository extends JpaRepository<SubjectDAO, Long> {

    SubjectDAO findSubjectDAOBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);
}
