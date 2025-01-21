package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); // Ensure this returns Optional<User>

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query(value = """
        SELECT SUM(oc.Quantity * p.Price)
        FROM Orders o
        JOIN OrderContains oc ON o.OrderID = oc.OrderID
        JOIN Product p ON oc.ProductID = p.ProductID
        WHERE o.UserID = :userId
        AND o.OrderDate >= CURRENT_DATE - INTERVAL 30 DAY
        """, nativeQuery = true)
    Double calculateTotalSpendingForLast30Days(@Param("userId") int userId);

    String findMembershipType(@Param("userId") int userId);

}