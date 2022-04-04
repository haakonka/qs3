package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.ParticipantInQueueDAO;
import edu.ntnu.Backend.repository.ParticipantInQueRepository;
import edu.ntnu.Backend.service.ParticipantInQueueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ParticipantInQueueServiceTest {
    java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2001-04-03 13:25:50.0");
    ParticipantInQueueDAO user1 = new ParticipantInQueueDAO(1,23,"alquid",2001,1,timestamp,0);
    List<ParticipantInQueueDAO> users = new LinkedList<>();
    List<ParticipantInQueueDAO> users2 = new LinkedList<>();
    ParticipantInQueueService participantInQueueService;
    @Mock
    ParticipantInQueRepository participantInQueRepository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        users.add(user1);
        participantInQueueService = new ParticipantInQueueService(participantInQueRepository);

        Mockito.when(participantInQueRepository.findAllBySubjectCodeAndSchoolYear("alquid",2001)).thenReturn(users);
    }

    @Test
    void findAllParticipantsInAQue() {
        Assertions.assertEquals(participantInQueueService.findAllParticipantsInAQue("alquid",2001),users);
        Assertions.assertNotEquals(participantInQueueService.findAllParticipantsInAQue("alquid",2001),users2);
    }
}
