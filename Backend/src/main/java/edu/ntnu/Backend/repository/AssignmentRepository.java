package edu.ntnu.Backend.repository;

import edu.ntnu.Backend.model.DAO.AssignmentDAO;
import edu.ntnu.Backend.model.DAO.AssignmentUserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<AssignmentDAO, Long> {
}
