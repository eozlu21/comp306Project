package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); // Ensure this returns Optional<User>

    Optional<User> findByUsernameAndPassword(String username, String password);

    @Query(value = """
        SELECT
            SUM(order_total) AS total_spending
        FROM (
            SELECT
                o.OrderID,
                SUM(oc.Quantity * p.Price) AS order_total
            FROM Orders o
            JOIN OrderContains oc ON o.OrderID = oc.OrderID
            JOIN Product p ON oc.ProductID = p.ProductID
            WHERE o.UserID = :userId
            AND o.OrderDate >= CURRENT_DATE - INTERVAL 30 DAY
            GROUP BY o.OrderID
            HAVING SUM(oc.Quantity * p.Price) > 0
        ) AS subquery
        """, nativeQuery = true)
    Double calculateTotalSpendingForLast30Days(@Param("userId") int userId);
}