package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.ParticipantInQueueDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantInQueRepository extends JpaRepository<ParticipantInQueueDAO, Long> {

    List<ParticipantInQueueDAO> findAllBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);

    List<ParticipantInQueueDAO> findParticipantInQueDAOBySubjectCodeAndSchoolYearAndUserID(String subjectCode, int schoolYear, int userId);

    long deleteParticipantInQueDAOByParticipantInQueID(int participantInQueId);

    ParticipantInQueueDAO findParticipantInQueDAOByParticipantInQueID(int participantInQueId);
}
