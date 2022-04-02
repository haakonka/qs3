package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.QueDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueRepository extends JpaRepository<QueDAO, Long> {

    QueDAO findQueDAOBySubjectCodeAndSchoolYear(String subjectCode, int SchoolYear);
}
