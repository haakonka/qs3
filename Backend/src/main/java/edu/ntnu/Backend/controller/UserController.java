package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.NewUserDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.model.DTO.UserbyIdDTO;
import edu.ntnu.Backend.service.*;
import org.codehaus.plexus.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The main controller for the api requests related to a user.
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final EmailService emailService;

    public UserController(UserService userService, AutenticationService autenticationService,
                          UserSubjectService userSubjectService, EmailService emailService) {
        this.userService = userService;
        this.autenticationService = autenticationService;
        this.userSubjectService = userSubjectService;
        this.emailService = emailService;
    }

    /**
     * A method to add a new user from a csv file. Only allowed for a user of role admin.
     * @param newUserDTO The specific format of data that is needed.
     *                   See {@link edu.ntnu.Backend.model.DTO.NewUserDTO NewUserDTO} for more information.
     * @return
     * @throws MessagingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/admin/addUserToSubject")
    public ResponseEntity addNewUserFromFile(@RequestBody NewUserDTO newUserDTO)
            throws MessagingException, NoSuchAlgorithmException {
        System.out.println("Token:" + newUserDTO.getToken());
        System.out.println("users:" + newUserDTO.getEmail());
        System.out.println("Subject:" + newUserDTO.getSubjectCode());
        if (autenticationService.checkIfAuthorized(newUserDTO.getToken(), 2)) {
            if (userService.findByEmail(newUserDTO.getEmail()) == null) {
                String password = userService.makePassword();
                String salt = userService.generateSalt();
                MessageDigest md = MessageDigest.getInstance("SHA-512");

                md.update(Base64.decodeBase64(salt.getBytes(StandardCharsets.UTF_8)));
                String hashedPass = new String(
                        Base64.encodeBase64(md.digest(password.getBytes(StandardCharsets.UTF_8))));
                System.out.println(newUserDTO.getEmail() + "sent email");
                emailService.sendAsHtml(newUserDTO.getEmail(), "Added in qs3 System",
                        "<h2>Hey,</h2><p>your password and email:\n" +
                                "password: " + password + "\nemail: " + newUserDTO.getEmail() + "</p>");
                UserDAO userDAO = new UserDAO(newUserDTO.getFirstname(), newUserDTO.getLastname(), hashedPass, salt,
                        newUserDTO.getEmail(), 0);

                userService.saveUser(userDAO);
                System.out.println("adding new user to a subject: " + newUserDTO.getEmail() + " in: "
                        + newUserDTO.getSubjectCode());
                UserSubjectDAO userSubjectDAO = new UserSubjectDAO(
                        newUserDTO.getSubjectYear(), newUserDTO.getSubjectCode(),
                        userService.findByEmail(newUserDTO.getEmail()).getId(), 0);

                userSubjectService.saveSubjectUser(userSubjectDAO);

                return ResponseEntity.ok().body(null);
            } else {
                UserSubjectDAO userSubjectDAO = new UserSubjectDAO(
                        newUserDTO.getSubjectYear(), newUserDTO.getSubjectCode(),
                        userService.findByEmail(newUserDTO.getEmail()).getId(), 0);
                userSubjectService.saveSubjectUser(userSubjectDAO);
                return ResponseEntity.ok().body(null);
            }
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    @PostMapping("user/getUser")
    public ResponseEntity getStudentByStudentID(@RequestBody UserbyIdDTO userbyIdDTO) {

        if (autenticationService.checkIfAuthorized(userbyIdDTO.getToken(), 0)) {
            return ResponseEntity.ok().body(userService.findNameByUserID(userbyIdDTO.getUserID()));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }
}
