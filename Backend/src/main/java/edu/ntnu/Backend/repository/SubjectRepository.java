package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectDAO, Long> {

    SubjectDAO findSubjectDAOBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);
}
