package edu.ntnu.Backend.service;
import edu.ntnu.Backend.dto.UserDTO;

import java.security.SecureRandom;
import java.util.ArrayList;

public class UserDTOService {
    ArrayList<UserDTO> users = new ArrayList<>();

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    //TODO add dehashing and rehasing
    public boolean checkIfMatch(UserDTO user) {
        for(int i = 0; i< users.size(); i++) {
            if(users.get(i).getEmail().contentEquals(user.getEmail()) &&
            users.get(i).getPassword().contentEquals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }

    //Some text to check something
}
