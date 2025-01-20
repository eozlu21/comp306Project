package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.OrderContains;
import edu.ku.comp306.ecommerce.entity.OrderContainsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderContainsRepository extends JpaRepository<OrderContains, OrderContainsId> {
    // Find all products in an order
    @Query(value = "SELECT * FROM OrderContains WHERE OrderID = ?1", nativeQuery = true)
    List<OrderContains> findByOrderId(Integer orderId);

    // Find all orders containing a specific product
    @Query(value = "SELECT * FROM OrderContains WHERE ProductID = ?1", nativeQuery = true)
    List<OrderContains> findByProductId(Integer productId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO OrderContains (OrderID, ProductID, Quantity) VALUES (:orderId, :productId, :quantity)", nativeQuery = true)
    void createOrderContains(@Param("orderId") Integer orderId, @Param("productId") Integer productId, @Param("quantity") Integer quantity);
}