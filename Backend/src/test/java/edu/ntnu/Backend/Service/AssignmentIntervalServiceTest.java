package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.AssignmentIntervalDAO;
import edu.ntnu.Backend.repository.AssignmentIntervalRepository;
import edu.ntnu.Backend.service.AssignmentIntervalService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

class AssignmentIntervalServiceTest {
    AssignmentIntervalDAO alquid1 = new AssignmentIntervalDAO(1,"alquid",2001,10,9,11,1);
    AssignmentIntervalDAO alquid2 = new AssignmentIntervalDAO(1,"alquid",2001,10,9,11,1);
    AssignmentIntervalDAO idatt2085 = new AssignmentIntervalDAO(4,"idatt2085",2075,1,1,4,1);
    ArrayList<AssignmentIntervalDAO> alquids = new ArrayList<>();
    AssignmentIntervalService assignmentIntervalService;
    @Mock
    AssignmentIntervalRepository assignmentIntervalRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        alquids.add(alquid1);
        alquids.add(alquid2);

        assignmentIntervalService = new AssignmentIntervalService(assignmentIntervalRepository);

        Mockito.when(assignmentIntervalRepository.findAssignmentIntervalDAOBySubjectCodeAndSchoolYear("alquid", 2001)).thenReturn(alquids);
    }

    @Test
    void findBySubjectCodeAndYearPositive() {
        Assertions.assertEquals(assignmentIntervalService.findBySubjectCodeAndYear("alquid",2001),alquids);
    }

    @Test
    void findBySubjectCodeAndYearNegativeValues(){
        Assertions.assertNull(assignmentIntervalService.findBySubjectCodeAndYear("wrong",2001));
        Assertions.assertNull(assignmentIntervalService.findBySubjectCodeAndYear("alquid",1700));
    }

}