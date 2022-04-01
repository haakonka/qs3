package edu.ntnu.Backend.service;


import edu.ntnu.Backend.model.DAO.UserDAO;
import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import edu.ntnu.Backend.repository.UserRepository;
import edu.ntnu.Backend.repository.UserSubjectRepository;
import edu.ntnu.Backend.repository.UserSubjectRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserSubjectService {
    private final UserSubjectRepository userSubjectRepository;

    public UserSubjectService(UserSubjectRepository userSubjectRepository) {
        this.userSubjectRepository = userSubjectRepository;
    }


    public List<UserSubjectDAO> findByUserId(int userId) {
        System.out.println("finding userSubject by userID: " + userId);
        return userSubjectRepository.findUserSubjectDAOByUserId(userId);
    }
}
