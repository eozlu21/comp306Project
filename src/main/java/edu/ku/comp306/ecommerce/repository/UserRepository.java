package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Find user by email
    User findByEmail(String email);
}