package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); // Ensure this returns Optional<User>

    Optional<User> findByUsernameAndPassword(String username, String password);
}