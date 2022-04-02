package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.UserSubjectDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubjectDAO, Long> {

    List<UserSubjectDAO> findUserSubjectDAOByUserId(int userid);

    
}
