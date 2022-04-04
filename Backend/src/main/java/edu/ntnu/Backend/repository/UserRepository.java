package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A class meant to access the user table in the database.
 * This class contains some premade methods and three custom-made ones.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDAO, Long> {

    UserDAO findByEmail(String email);

    UserDAO findById(int id);

    Boolean deleteUserDAOByEmail(String email);
}
