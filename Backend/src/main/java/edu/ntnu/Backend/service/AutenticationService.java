package edu.ntnu.Backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import edu.ntnu.Backend.model.DAO.UserDAO;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class AutenticationService {
    //make jwt TOKEN with name, role, epost
    private UserService userService;

    public AutenticationService(UserService userService) {
        this.userService = userService;
    }

    public boolean attemptAuthentication(String email, String password) throws AuthenticationException, NoSuchAlgorithmException {
        System.out.println("The email was: " + email + " And the password is: " + password );
        UserDAO user = userService.findByEmail(email);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(Base64.getDecoder().decode(user.getSalt()));
        byte[] hasedPassword = Base64.getEncoder().encode(md.digest(password.getBytes(StandardCharsets.UTF_8)));

        if(new String(hasedPassword).equals(new String(user.getHash().getBytes(StandardCharsets.UTF_8)))){
            return true;
        }
        System.out.println("Attmepted: " + new String(hasedPassword));
        System.out.println("Real     : " + new String(user.getHash().getBytes(StandardCharsets.UTF_8)));
        return false;
    }

    public String successfulAuthentication(UserDAO user) throws IOException, ServletException {
        ArrayList<String> privs = new ArrayList<>();
        for (int i = 0; i<= user.getRoles(); i++){
            privs.add(String.valueOf(i));
        }

        Algorithm algorithm = Algorithm.HMAC256("tiL8yZXjlEvxKImZS0YeIQC5V29PFDcm2wSHn47texw6fpNKv34uqyWe/NUz5go3aAkRflcDFVfpfYwoLPZrFA==".getBytes(StandardCharsets.UTF_8));
        System.out.println("Making jwt");
        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 60 * 48 * 60* 1000))
                .withClaim("roles", privs)
                .sign(algorithm);

        System.out.println("You were autenticated");
        return access_token;
    }


}
