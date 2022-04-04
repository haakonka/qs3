package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.repository.UserSubjectRepository;
import edu.ntnu.Backend.service.UserService;
import edu.ntnu.Backend.service.UserSubjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UserSubjectServiceTest {

    UserSubjectDAO userSubjectDAO1 = new UserSubjectDAO(2011,"Subjectcode1",1,0);

    UserSubjectService userSubjectService;

    @Mock
    UserSubjectRepository userSubjectRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userSubjectService = new UserSubjectService(userSubjectRepository);

    }

    @Test
    void findAllUsersInSubject(){

    }

    @Test
    void findByUserId() {

    }

}