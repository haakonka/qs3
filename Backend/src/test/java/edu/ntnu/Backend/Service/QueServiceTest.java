package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.QueDAO;
import edu.ntnu.Backend.repository.AssignmentIntervalRepository;
import edu.ntnu.Backend.repository.QueRepository;
import edu.ntnu.Backend.service.QueService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class QueServiceTest {

    @Mock
    QueRepository queRepository;

    QueDAO que1 = new QueDAO("alquid",2001,0);
    QueDAO que2 = new QueDAO("idatt2085",2075,1);
    QueService queService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(queRepository.findQueDAOBySubjectCodeAndSchoolYear("idatt2085",2075)).thenReturn(que2);

        queService = new QueService(queRepository);
    }

    @Test
    void findAQue() {
        Assertions.assertEquals(queService.findAQueBySubjectCodeAndSchoolYear("idatt2085",2075),que2);
    }
}