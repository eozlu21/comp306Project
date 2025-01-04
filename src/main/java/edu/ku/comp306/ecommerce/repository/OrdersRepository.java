package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    // Find orders by user ID
    List<Orders> findByUserId(Integer userId);

}