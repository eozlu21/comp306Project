package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Cart;
import edu.ku.comp306.ecommerce.entity.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartId> {

    // Find all cart items for a specific user
    @Query(value = "SELECT * FROM Cart WHERE UserID = ?1", nativeQuery = true)
    List<Cart> findByUserId(Integer userId);

    // Find a specific cart item by userId and productId
    @Query(value = "SELECT * FROM Cart WHERE UserID = ?1 AND ProductID = ?2", nativeQuery = true)
    Optional<Cart> findByUserIdAndProductId(Integer userId, Integer productId);

    // Delete a specific cart item by userId and productId
    @Modifying
    @Query(value = "DELETE FROM Cart WHERE UserID = ?1 AND ProductID = ?2", nativeQuery = true)
    void deleteByUserIdAndProductId(Integer userId, Integer productId);

    @Modifying
    @Query(value = "UPDATE Cart SET Quantity = ?3 WHERE UserID = ?1 AND ProductID = ?2", nativeQuery = true)
    void editQuantity(Integer userId, Integer productId, Integer quantity);

    @Modifying
    @Query(value = "INSERT INTO Cart VALUES (?1, ?2, ?3)", nativeQuery = true)
    void save(Integer userId, Integer productId, Integer quantity);
}