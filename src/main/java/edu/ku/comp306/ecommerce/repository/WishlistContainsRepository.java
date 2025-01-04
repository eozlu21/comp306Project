package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.WishlistContains;
import edu.ku.comp306.ecommerce.entity.WishlistContainsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistContainsRepository extends JpaRepository<WishlistContains, WishlistContainsId> {
    // Find all products in a wishlist
    @Query(value = "SELECT * FROM WishlistContains WHERE WLID = ?1", nativeQuery = true)
    List<WishlistContains> findByWishlistId(Integer wishlistId);

    // Find all wishlists containing a specific product
    @Query(value = "SELECT * FROM WishlistContains WHERE ProductID = ?1", nativeQuery = true)
    List<WishlistContains> findByProductId(Integer productId);
}