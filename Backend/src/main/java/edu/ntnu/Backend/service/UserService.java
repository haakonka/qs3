package edu.ntnu.Backend.service;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DTO.NewUserDTO;
import edu.ntnu.Backend.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Service
public class UserService{
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean deleteUserDAO(String email) {
        System.out.println("we deleted user with the email: " + email);
        if(userRepository.deleteUserDAOByEmail(email)){
            return true;
        }
        return false;
    }


    public UserDAO findByEmail(String email) {
        System.out.println("FINDING USER WITH EMAIL: " + email);
        return userRepository.findByEmail(email);
    }

    public String findNameByUserID(int id){
        UserDAO userDAO = userRepository.findById(id);
        String name = userDAO.getFirstName() + " " + userDAO.getLastName();
        return name;
    }

    public void saveUser(UserDAO user){
        userRepository.save(user);
    }


    //just testing methode
    public List<UserDAO> findAll() {
        return userRepository.findAll();
    }

    //gotta fix
    public boolean CheckIfUserExists(String email) {
        if(userRepository.findByEmail(email)!= null){
            return true;
        }

        return false;
    }

    public String makePassword(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    };

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


}
