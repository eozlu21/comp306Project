package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Cart;
import edu.ku.comp306.ecommerce.entity.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {
    // Find cart by user ID
    List<Cart> findByUserId(Integer userId);

    // Find cart by product ID
    List<Cart> findByProductId(Integer productId);

    @Modifying
    @Query(value = """
        INSERT INTO Cart (UserID, ProductID, Quantity)
        VALUES (:userId, :productId, :quantity)
        """, nativeQuery = true)
    void save(@Param("userId") Integer userId,
              @Param("productId") Integer productId,
              @Param("quantity") Integer quantity);


}
