package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.ParticipantInQueDAO;
import edu.ntnu.Backend.repository.ParticipantInQueRepository;
import edu.ntnu.Backend.service.ParticipantInQueService;
import edu.ntnu.Backend.service.UserService;
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
public class ParticipantInQueServiceTest {
    java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf("2001-04-03 13:25:50.0");
    ParticipantInQueDAO user1 = new ParticipantInQueDAO(1,23,"alquid",2001,1,timestamp,0);
    List<ParticipantInQueDAO> users = new LinkedList<>();
    List<ParticipantInQueDAO> users2 = new LinkedList<>();
    ParticipantInQueService participantInQueService;
    @Mock
    ParticipantInQueRepository participantInQueRepository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        users.add(user1);
        participantInQueService = new ParticipantInQueService(participantInQueRepository);

        Mockito.when(participantInQueRepository.findAllBySubjectCodeAndSchoolYear("alquid",2001)).thenReturn(users);
    }

    @Test
    void findAllParticipantsInAQue() {
        Assertions.assertEquals(participantInQueService.findAllParticipantsInAQue("alquid",2001),users);
        Assertions.assertNotEquals(participantInQueService.findAllParticipantsInAQue("alquid",2001),users2);
    }
}
