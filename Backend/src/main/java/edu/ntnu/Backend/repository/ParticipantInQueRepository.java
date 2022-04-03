package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.ParticipantInQueDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantInQueRepository extends JpaRepository<ParticipantInQueDAO, Long> {

    List<ParticipantInQueDAO> findAllBySubjectCodeAndSchoolYear(String subjectCode, int schoolYear);

    long deleteParticipantInQueDAOByParticipantInQueID(int participantInQueId);
}
