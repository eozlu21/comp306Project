package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.OrderContains;
import edu.ku.comp306.ecommerce.entity.OrderContainsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderContainsRepository extends JpaRepository<OrderContains, OrderContainsId> {
    // Find all products in an order
    @Query(value = "SELECT * FROM OrderContains WHERE OrderID = ?1", nativeQuery = true)
    List<OrderContains> findByOrderId(Integer orderId);

    // Find all orders containing a specific product
    @Query(value = "SELECT * FROM OrderContains WHERE ProductID = ?1", nativeQuery = true)
    List<OrderContains> findByProductId(Integer productId);
}