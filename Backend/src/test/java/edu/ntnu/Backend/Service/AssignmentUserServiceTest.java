package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.repository.AssignmentUserRepository;
import edu.ntnu.Backend.repository.UserRepository;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AssignmentUserServiceTest {

    @Mock
    AssignmentUserRepository assignmentUserRepository;

    @BeforeAll
    void setup(){

    }

    @Test
    void findBySubjectCodeAndYearAndUserID(){

    }

    @Test
    void findAllSubjectsByUserID(){

    }


}