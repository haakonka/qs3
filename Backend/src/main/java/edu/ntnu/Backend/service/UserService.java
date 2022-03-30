package edu.ntnu.Backend.service;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Service @Transactional @RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDAO userDAO = userRepository.findByEmail(email);
        if (userDAO == null){
            System.err.println("User does not exist/ or cant be found in the database");
            throw new UsernameNotFoundException("User does not exist/ or cant be found in the database");
        } else{
            System.out.println("Found user in DB by email: " + userDAO.getEmail());
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(int i = 0; i<= userDAO.getRoles();i++){
            authorities.add(new SimpleGrantedAuthority(String.valueOf(i)));
        }
        return new org.springframework.security.core.userdetails.User(userDAO.getEmail(),userDAO.getHash(),authorities);
    }

    public UserDAO saveUserDAO(UserDAO userDAO){
        //ADD VALIDATION
        System.out.println("SAVING USER");
        userDAO.setHash(passwordEncoder.encode(userDAO.getHash()));
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
