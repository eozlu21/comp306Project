package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Orders;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Modifying
    @Query(value = "INSERT INTO Orders (UserID, OrderDate) VALUES (?1, ?2)", nativeQuery = true)
    @Transactional
    void insertOrder(Integer userId, LocalDate orderDate);


    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    int getLastInsertId();

}