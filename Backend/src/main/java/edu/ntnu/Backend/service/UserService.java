package edu.ntnu.Backend.service;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service @Transactional @RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDAO saveUserDAO(UserDAO userDAO){
        //ADD VALIDATION
        System.out.println("SAVING USER");
        return userRepository.save(userDAO);
    }

    public void deleteUserDAO(UserDAO userDAO){
        System.out.println("we deleted user with the email: " + userDAO.getEmail());
        userRepository.delete(userDAO);
    }


    public UserDAO findByEmail(String email){
        System.out.println("FINDING USER WITH EMAIL: " + email);
        return userRepository.findByEmail(email);
    }






    //just testing methode
    public List<UserDAO> findAll(){
        return userRepository.findAll();
    }

    //gotta fix
    public boolean CheckIfUserExists(String email, String password){
        userRepository.findAll();

        return true;
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

}
