package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

    UserDAO findByEmail(String email);

    UserDAO findById(int id);

    Boolean deleteUserDAOByEmail(String email);

}
