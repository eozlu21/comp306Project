package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Orders;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    // Find orders by user ID
    List<Orders> findByUserId(Integer userId);

    @Query(value = """
        SELECT COUNT(*)
        FROM Orders O
            JOIN OrderContains OC on O.OrderID = OC.OrderID
        WHERE OC.ProductID = ?1 AND O.UserID = ?2""",
            nativeQuery = true)
    int getOrderedCount(Integer productId, Integer userId);
}