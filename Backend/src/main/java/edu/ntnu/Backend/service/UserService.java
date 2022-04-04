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

/**
 * A class meant to use the access-point made to the user table in the database.
 * This class uses the methods from the
 * {@link edu.ntnu.Backend.repository.UserRepository UserRepository}
 */
@Service
public class UserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * A method to delete a user from the database.
     * @param email the email of the user to delete.
     * @return Returns true if the user was deleted and false if not.
     */
    public boolean deleteUserDAO(String email) {
        System.out.println("we deleted user with the email: " + email);
        if(userRepository.deleteUserDAOByEmail(email)){
            return true;
        }
        return false;
    }

    /**
     * A method to find a user in the database by email.
     * @param email the email of the user.
     * @return Returns the userDAO object matching the email.
     */
    public UserDAO findByEmail(String email) {
        System.out.println("FINDING USER WITH EMAIL: " + email);
        return userRepository.findByEmail(email);
    }

    /**
     * A method to find the name of a user by user id.
     * @param id the id of the user.
     * @return Returns the name of the user matching this id.
     */
    public String findNameByUserID(int id){
        UserDAO userDAO = userRepository.findById(id);
        String name = userDAO.getFirstName() + " " + userDAO.getLastName();
        return name;
    }

    /**
     * A method to save a user to the database.
     * @param user the userDAO representing the user to add to the database.
     */
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

    /**
     * A method to hash a password with a salt.
     * @return Returns a Base64 encoded version of the hashed password.
     */
    public String makePassword(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    };

    /**
     * A method to generate a salt.
     * @return returns a Base64 encoded version of the salt.
     */
    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


}
