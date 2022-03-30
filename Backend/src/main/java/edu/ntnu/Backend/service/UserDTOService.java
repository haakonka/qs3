package edu.ntnu.Backend.service;
import edu.ntnu.Backend.dto.UserDTO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class UserDTOService {
    ArrayList<UserDTO> users = new ArrayList<>();

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[64];
        random.nextBytes(salt);
        return salt;
    }

    //TODO add dehashing and rehasing
    public boolean checkIfMatch(UserDTO user) {
        for(int i = 0; i< users.size(); i++) {
            if(users.get(i).getEmail().contentEquals(user.getEmail())) {
                //TODO hmmm what is this
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-512");
                    byte[] salt = generateSalt();
                    md.update(salt);
                    byte[] hashedPassword = md.digest(user.getPassword().getBytes(StandardCharsets.UTF_8));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    //Some text to check something
}
