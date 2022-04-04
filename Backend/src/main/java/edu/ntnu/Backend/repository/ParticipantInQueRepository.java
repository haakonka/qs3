package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.ParticipantInQueDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * A class meant to access the participantInQue table in the database.
 * This class contains some premade methods and four custom-made ones.
 */
@Repository
public interface ParticipantInQueRepository extends JpaRepository<ParticipantInQueDAO, Long> {

    List<ParticipantInQueDAO> findAllBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);

    List<ParticipantInQueDAO> findParticipantInQueDAOBySubjectCodeAndSchoolYearAndUserID(String subjectCode, int schoolYear, int userId);

    long deleteParticipantInQueDAOByParticipantInQueID(int participantInQueId);

    ParticipantInQueDAO findParticipantInQueDAOByParticipantInQueID(int participantInQueId);
}
