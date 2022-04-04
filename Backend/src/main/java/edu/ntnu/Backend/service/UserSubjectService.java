package edu.ntnu.Backend.service;


import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.model.DTO.NewUserDTO;
import edu.ntnu.Backend.repository.UserRepository;
import edu.ntnu.Backend.repository.UserSubjectRepository;
import edu.ntnu.Backend.repository.UserSubjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * A class meant to use the access-point made to the usersubject table in the database.
 * This class uses the methods from the
 * {@link edu.ntnu.Backend.repository.UserSubjectRepository UserSubjectRepository}
 */
@Service
@Transactional
public class UserSubjectService {
    private final UserSubjectRepository userSubjectRepository;

    public UserSubjectService(UserSubjectRepository userSubjectRepository) {
        this.userSubjectRepository = userSubjectRepository;
    }

    /**
     * A method to save a new userSubject to the database.
     * @param userSubjectDAO the userSubject to be saved.
     */
    public void saveSubjectUser(UserSubjectDAO userSubjectDAO){
        this.userSubjectRepository.save(userSubjectDAO);
    }

    /**
     * A method to find all users within a subject.
     * @param schoolYear the school year of the subject.
     * @param subjectCode the subject code of the subject.
     * @return Returns a list of all the userSubjectDAO objects found.
     */
    public List<UserSubjectDAO> findAllUsersInSubject(int schoolYear, String subjectCode){
        return userSubjectRepository.findUserSubjectDAOBySchoolYearAndSubjectCode(schoolYear,subjectCode);
    }

    /**
     * A method to find all subjects for a user.
     * @param userId the user you want subjects for.
     * @return Returns a list of UserSubjectDAO objects found for this user.
     */
    public List<UserSubjectDAO> findByUserId(int userId) {
        System.out.println("finding userSubject by userID: " + userId);
        return userSubjectRepository.findUserSubjectDAOByUserId(userId);
    }
}
