package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.repository.UserRepository;
import edu.ntnu.Backend.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    UserDAO adminUser = new UserDAO("Omar","Sheikh","AxiQlCALbe08ThcLQWkPi+TwbnweXnOcAX8hhJBEaV9zcstUBxP7CDQ40wJt8pK6Ue72J0IOZ2JsY5ZMvp+ybQ==","ThisIsASalt","omar@omar.com",2);
    UserDAO studassUser = new UserDAO("haakon","kanter","AxiQlCALbe08ThcLQWkPi+TwbnweXnOcAX8hhJBEaV9zcstUBxP7CDQ40wJt8pK6Ue72J0IOZ2JsY5ZMvp+ybQ==","ThisIsASalt","haakon@haakon.com",1);
    UserDAO user = new UserDAO("Håkon","R","ThisIsAHash","ThisIsASalt","hakon@hakon.com",0);
    ArrayList<UserDAO> allUsers = new ArrayList<>();

    UserService userService;
    @Mock
    UserRepository userRepository;

    @BeforeAll
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);

        Mockito.when(userRepository.findByEmail("omar@omar.com")).thenReturn(adminUser);
        Mockito.when(userRepository.findByEmail("haakon@haakon.com")).thenReturn(studassUser);
        Mockito.when(userRepository.findByEmail("hakon@hakon.com")).thenReturn(user);
        Mockito.when(userRepository.deleteUserDAOByEmail("omar@omar.com")).thenReturn(true);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
    }

    @Test
    void findByEmail() {
        Assertions.assertEquals(userService.findByEmail("omar@omar.com"),adminUser);
        Assertions.assertNull(userService.findByEmail("InvalidEmail"));
    }

    @Test
    void findAll() {
        Assertions.assertEquals(userService.findAll(), allUsers);
    }

    @Test
    void checkIfUserExists() {
        Assertions.assertTrue(userService.CheckIfUserExists("omar@omar.com"));
        Assertions.assertFalse(userService.CheckIfUserExists("tullemail@Tull.tull"));
    }

    @Test
    void generateSalt() {
        Assertions.assertNotNull(userService.generateSalt());
    }

    @Test
    void deleteUserDAOByEmail() {
        Assertions.assertTrue(userService.deleteUserDAO("omar@omar.com"));
        Assertions.assertFalse(userService.deleteUserDAO("wrongEmail@wrong.com"));
    }

}