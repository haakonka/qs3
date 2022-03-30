package edu.ntnu.Backend.service;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public boolean checkIfMatch(String email, String password) {
        for(int i = 0; i< users.size(); i++) {
            if(users.get(i).getEmail().contentEquals(email)) {
                //TODO hmmm what is this
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    byte[] salt = generateSalt();
                    md.update(salt);

                    //Password is hashed and ready to be stored.
                    byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

                    String hash = new String(hashedPassword, StandardCharsets.UTF_8);

                    if(users.get(i).getHash().contentEquals(hash)) {
                        return true;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    //Some text to check something
}
