package edu.ntnu.Backend.Service;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.repository.UserRepository;
import edu.ntnu.Backend.service.AutenticationService;
import edu.ntnu.Backend.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AutenticationServiceTest {
    UserDAO adminUser = new UserDAO("Omar","Sheikh","AxiQlCALbe08ThcLQWkPi+TwbnweXnOcAX8hhJBEaV9zcstUBxP7CDQ40wJt8pK6Ue72J0IOZ2JsY5ZMvp+ybQ==","ThisIsASalt","omar@omar.com",2);
    UserDAO studassUser = new UserDAO("haakon","kanter","AxiQlCALbe08ThcLQWkPi+TwbnweXnOcAX8hhJBEaV9zcstUBxP7CDQ40wJt8pK6Ue72J0IOZ2JsY5ZMvp+ybQ==","ThisIsASalt","haakon@haakon.com",1);
    UserDAO user = new UserDAO("Håkon","R","ThisIsAHash","ThisIsASalt","hakon@hakon.com",0);
    ArrayList<UserDAO> allUsers = new ArrayList<>();


    @Mock
    UserRepository userRepository;

    UserService userService;
    AutenticationService autenticationService;

    @BeforeAll
    void setup(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
        autenticationService = new AutenticationService(userService);
        allUsers.add(adminUser);
        allUsers.add(studassUser);
        allUsers.add(user);

        Mockito.when(userRepository.findByEmail("omar@omar.com")).thenReturn(adminUser);
        Mockito.when(userRepository.findByEmail("haakon@haakon.com")).thenReturn(studassUser);
        Mockito.when(userRepository.findByEmail("hakon@hakon.com")).thenReturn(user);
        Mockito.when(userRepository.findAll()).thenReturn(allUsers);
    }

    @Test
    void attemptAuthentication() throws NoSuchAlgorithmException {
        Assertions.assertTrue(autenticationService.attemptAuthentication("omar@omar.com","passord"));
        Assertions.assertFalse(autenticationService.attemptAuthentication("omar@omar.com","feilpassord"));
    }

    @Test
    void successfulAuthentication() throws ServletException, IOException {
        Assertions.assertNotNull(autenticationService.successfulAuthentication(adminUser));
    }

    @Test
    void checkIfAuthorized() throws ServletException, IOException {
        String adminToken = autenticationService.successfulAuthentication(adminUser);
        String studassToken = autenticationService.successfulAuthentication(studassUser);
        String userToken = autenticationService.successfulAuthentication(user);
        //for admin privilege api calls
        Assertions.assertTrue(autenticationService.checkIfAuthorized(adminToken,2));
        Assertions.assertFalse(autenticationService.checkIfAuthorized(studassToken,2));
        Assertions.assertFalse(autenticationService.checkIfAuthorized(userToken,2));
        //for studass api calls
        Assertions.assertTrue(autenticationService.checkIfAuthorized(adminToken,1));
        Assertions.assertTrue(autenticationService.checkIfAuthorized(studassToken,1));
        Assertions.assertFalse(autenticationService.checkIfAuthorized(userToken,1));
        //for user api calls
        Assertions.assertTrue(autenticationService.checkIfAuthorized(adminToken,0));
        Assertions.assertTrue(autenticationService.checkIfAuthorized(studassToken,0));
        Assertions.assertTrue(autenticationService.checkIfAuthorized(userToken,0));
    }

    @Test
    void getUserFromJWT() throws ServletException, IOException {
        String adminToken = autenticationService.successfulAuthentication(adminUser);

        Assertions.assertEquals(autenticationService.getUserFromJWT(adminToken),adminUser);

    }
}