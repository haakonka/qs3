package edu.ntnu.Backend.service;

import java.security.SecureRandom;

public class UserDTOService {
    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
