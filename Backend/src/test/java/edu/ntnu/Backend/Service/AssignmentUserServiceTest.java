package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import edu.ntnu.Backend.repository.AssignmentUserRepository;
import edu.ntnu.Backend.repository.UserRepository;
import edu.ntnu.Backend.service.AssignmentUserService;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentUserServiceTest {
    AssignmentUserDAO aui3 = new AssignmentUserDAO(3,81,"alquid",2001,10,1);
    AssignmentUserDAO aui4 = new AssignmentUserDAO(4,81,"alquid",2001,9,0);
    AssignmentUserDAO aui6 = new AssignmentUserDAO(6,83,"idatt2085",2075,1,0);
    ArrayList<AssignmentUserDAO> assignmentUserDAOS81 = new ArrayList<>();
    ArrayList<AssignmentUserDAO> assignmentUserDAOS83 = new ArrayList<>();
    AssignmentUserService assignmentUserService;

    @Mock
    AssignmentUserRepository assignmentUserRepository;


    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);

        assignmentUserDAOS81.add(aui3);
        assignmentUserDAOS81.add(aui4);
        assignmentUserDAOS83.add(aui6);

        assignmentUserService = new AssignmentUserService(assignmentUserRepository);

        Mockito.when(assignmentUserRepository.findAssignmentUserDAOByAssignmentUserID(6)).thenReturn(aui6);
        Mockito.when(assignmentUserRepository.findAssignmentUserDAOByUserID(83)).thenReturn(assignmentUserDAOS81);
        Mockito.when(assignmentUserRepository.findAssignmentUserDAOBySubjectCodeAndSchoolYearAndUserID("alquid",2001,81)).thenReturn(assignmentUserDAOS83);

    }

    @Test
    void findBySubjectCodeAndYearAndUserIdPositive(){
        Assertions.assertEquals(assignmentUserService.findBySubjectCodeAndYearAndUserID("alquid",2001,81),assignmentUserDAOS83);

    }

    @Test
    void findBySubjectCodeAndYearAndUserIdNegative() {
        Assertions.assertNull(assignmentUserService.findBySubjectCodeAndYearAndUserID("wrong", 2001, 81));
        Assertions.assertNull(assignmentUserService.findBySubjectCodeAndYearAndUserID("alquid", 2000, 81));
        Assertions.assertNull(assignmentUserService.findBySubjectCodeAndYearAndUserID("alquid", 2001, 1));
    }

    @Test
    void findAllSubjectsByUserID(){
        Assertions.assertEquals(assignmentUserService.findAllSubjectsByUserID(83),assignmentUserDAOS81);
    }


    @Test
    void findAssignmentByUserIdPositive(){
        Assertions.assertEquals(assignmentUserService.findAssignmentUserById(6),aui6);
    }

    @Test
    void findAssignmentByUserIdNegative(){
        Assertions.assertNull(assignmentUserService.findAssignmentUserById(7));
    }


}