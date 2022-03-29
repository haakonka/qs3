package edu.ntnu.Backend.service;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    ArrayList<UserDAO> users = new ArrayList<>();


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDAO> findAll(){
        return userRepository.findAll();
    }

    //gotta fix
    public boolean CheckIfUserExists(String email, String password){
        userRepository.findAll();

        return true;
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    //TODO add dehashing and rehasing
    public boolean checkIfMatch(UserDAO user) {
        return true;
    }

    //Some text to check something
}
