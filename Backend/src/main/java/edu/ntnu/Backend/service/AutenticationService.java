package edu.ntnu.Backend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DTO.UserDTO;
import org.springframework.stereotype.Service;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Service
public class AutenticationService {
    //make jwt TOKEN with name, role, epost
    private UserService userService;
    private Algorithm algorithm = Algorithm.HMAC256("tiL8yZXjlEvxKImZS0YeIQC5V29PFDcm2wSHn47texw6fpNKv34uqyWe/NUz5go3aAkRflcDFVfpfYwoLPZrFA==".getBytes(StandardCharsets.UTF_8));

    public AutenticationService(UserService userService) {
        this.userService = userService;
    }

    public boolean attemptAuthentication(String email, String password) throws NoSuchAlgorithmException {
        System.out.println("The email was: " + email + " And the password is: " + password);
        UserDAO user = userService.findByEmail(email);
        if (user ==null){
            return false;
        }

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(Base64.getDecoder().decode(user.getSalt()));
        byte[] hasedPassword = Base64.getEncoder().encode(md.digest(password.getBytes(StandardCharsets.UTF_8)));

        if (new String(hasedPassword).equals(new String(user.getHash().getBytes(StandardCharsets.UTF_8)))) {
            return true;
        }
        System.out.println("Attmepted: " + new String(hasedPassword));
        System.out.println("Real     : " + new String(user.getHash().getBytes(StandardCharsets.UTF_8)));
        return false;
    }

    public String successfulAuthentication(UserDAO user) throws IOException, ServletException {
        ArrayList<String> privs = new ArrayList<>();
        for (int i = 0; i <= user.getRoles(); i++) {
            privs.add(String.valueOf(i));
        }

        Algorithm algorithm = Algorithm.HMAC256("tiL8yZXjlEvxKImZS0YeIQC5V29PFDcm2wSHn47texw6fpNKv34uqyWe/NUz5go3aAkRflcDFVfpfYwoLPZrFA==".getBytes(StandardCharsets.UTF_8));
        System.out.println("Making jwt");
        String access_token = JWT.create()
                .withSubject(user.getEmail())
                .withClaim("firstname", user.getFirstName())
                .withClaim("lastname", user.getLastName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 48 * 60 * 1000))
                .withClaim("roles", privs)
                .sign(algorithm);


        System.out.println("You were autenticated : " + access_token);
        return access_token;
    }

    public boolean checkIfAuthorized(String token, int requirment){
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        String userEmail = decodedJWT.getSubject();
        System.out.println("JWT useremail:" + userEmail);
        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
        System.out.println(roles.length-1);
        System.out.println(requirment);
        if((roles.length-1)>=requirment){
            return true;
        }
        return false;
    }

    public UserDAO getUserFromJWT(String token){
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return userService.findByEmail(decodedJWT.getSubject());
    }


}
