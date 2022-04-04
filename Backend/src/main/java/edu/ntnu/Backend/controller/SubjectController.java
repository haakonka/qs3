package edu.ntnu.Backend.controller;

import edu.ntnu.Backend.model.DAO.SubjectDAO;
import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.SubjectDTO;
import edu.ntnu.Backend.model.DTO.SubjectIdDTO;
import edu.ntnu.Backend.model.DTO.TokenDTO;
import edu.ntnu.Backend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * A controller for the api calls towards the subjects.
 */
@RequestMapping("/api")
@RestController
@CrossOrigin
public class SubjectController {
    private final AutenticationService autenticationService;
    private final UserSubjectService userSubjectService;
    private final SubjectService subjectService;

    public SubjectController(AutenticationService autenticationService, UserSubjectService userSubjectService,
                             SubjectService subjectService) {
        this.autenticationService = autenticationService;
        this.userSubjectService = userSubjectService;
        this.subjectService = subjectService;
    }

    /**
     * A method to get all the subjects for a given user.
     * @param token The specific format of data that is needed.
     *              See {@link edu.ntnu.Backend.model.DTO.TokenDTO TokenDTO} for more information.
     * @return Returns a list of the subjectDAOs contained within a response entity,
     * or a http status forbidden if the user is not logged-in.
     */
    @PostMapping("/user/subjects")
    public ResponseEntity getSubjectsForUser(@RequestBody TokenDTO token) {
        System.out.println("Tryng to acess all users");
        System.out.println("token:" + token.getToken());
        if (autenticationService.checkIfAuthorized(token.getToken(), 0)) {
            UserDAO user = autenticationService.getUserFromJWT(token.getToken());
            System.out.println(userSubjectService.findByUserId(user.getId()).get(0).getSubjectCode());
            // got list of subject that the user has in the form of subjectuser

            List<UserSubjectDAO> userSubjects = userSubjectService.findByUserId(user.getId());
            List<SubjectDAO> subjects = new LinkedList<>();
            for (int i = 0; i < userSubjects.size(); i++) {
                subjects.add(subjectService.findSubjectBySubjectId(
                        userSubjects.get(i).getSubjectCode(), userSubjects.get(i).getSchoolYear()));
            }
            return ResponseEntity.ok().body(subjects);
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method for an admin to add a subject.
     * @param subjectDTO The specific format of data that is needed.
     *                   See {@link edu.ntnu.Backend.model.DTO.SubjectDTO SubjectDTO} for more information.
     * @return Returns a response entity with the status of the request,
     * or a http status forbidden if the user is not logged-in.
     * The user also have to be of the role admin for the forbidden response to not be triggered.
     */
    @PostMapping("admin/addSubject")
    public ResponseEntity addSubject(@RequestBody SubjectDTO subjectDTO) {
        if (autenticationService.checkIfAuthorized(subjectDTO.getToken(), 2)) {
            SubjectDAO subjectDAO = new SubjectDAO(subjectDTO.getSubjectCode(),
                    Integer.parseInt(subjectDTO.getSchoolYear()), subjectDTO.getSubjectName(), 0);
            subjectService.saveNewSubject(subjectDAO);
            return ResponseEntity.ok().body("subjectAdded");
        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method for an admin to remove a subject.
     * @param subjectDTO The specific format of data that is needed.
     *                   See {@link edu.ntnu.Backend.model.DTO.SubjectDTO SubjectDTO} for more information.
     * @return Returns a response entity with the status of the request,
     * or a http status forbidden if the user is not logged-in.
     * The user also have to be of the role admin for the forbidden response to not be triggered.
     */
    @PostMapping("admin/removeSubject")
    public ResponseEntity removeSubject(@RequestBody SubjectDTO subjectDTO) {
        if (autenticationService.checkIfAuthorized(subjectDTO.getToken(), 2)) {
            SubjectDAO subjectDAO = new SubjectDAO(subjectDTO.getSubjectCode(),
                    Integer.parseInt(subjectDTO.getSchoolYear()), subjectDTO.getSubjectName(), 0);
            subjectService.removeSubject(subjectDAO);
            return ResponseEntity.ok().body("subjectRemoved");
        }

        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }

    /**
     * A method to get a specific subject for a user.
     * @param subjectIdDTO The specific format of data that is needed.
     *                   See {@link edu.ntnu.Backend.model.DTO.SubjectIdDTO SubjectIdDTO} for more information.
     * @return Returns a response entity containing the specific subject. As with the other methods of this class this
     * method may also return a http status forbidden if the user is not logged-in.
     */
    @PostMapping("user/subject")
    public ResponseEntity getASpecificSubject(@RequestBody SubjectIdDTO subjectIdDTO) {
        System.out.println("Trying to access a specific subject");
        System.out.println("Token:" + subjectIdDTO.getToken());
        System.out.println("Subject code:" + subjectIdDTO.getSubjectCode().replace("\\", ""));
        System.out.println("School year:" + subjectIdDTO.getSchoolYear());
        if (autenticationService.checkIfAuthorized(subjectIdDTO.getToken(), 0)) {
            System.out.println("The status of the que is: " + subjectService.findSubjectBySubjectId(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())).getStatusQue());
            return ResponseEntity.ok().body(subjectService.findSubjectBySubjectId(
                    subjectIdDTO.getSubjectCode().replace("\\", ""),
                    Integer.valueOf(subjectIdDTO.getSchoolYear())));
        }
        return new ResponseEntity("not authorized", HttpStatus.FORBIDDEN);
    }
}
