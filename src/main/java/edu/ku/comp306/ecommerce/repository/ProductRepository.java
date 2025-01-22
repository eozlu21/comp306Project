package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.enums.MembershipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Find products by brand
    List<Product> findByBrand(String brand);

    // Find products within a price range
    @Query(value = "SELECT * FROM Product WHERE Price BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Product> findByPriceRange(Float minPrice, Float maxPrice);

    @Query(value = "SELECT p.* " +
            "FROM Product p " +
            "JOIN OrderContains oc ON p.ProductID = oc.ProductID " +
            "GROUP BY p.ProductID " +
            "ORDER BY COUNT(oc.ProductID) DESC " +
            "LIMIT 6",
            nativeQuery = true)
    List<Product> findPopularProducts();

    @Query(value = """
        SELECT p FROM Product p
        WHERE p.productId IN (
            SELECT l.productId FROM Laptop l WHERE :category = 'laptop'
            UNION
            SELECT ph.productId FROM Phone ph WHERE :category = 'phone'
            UNION
            SELECT c.productId FROM Camera c WHERE :category = 'camera'
        )
    """)
    List<Product> findProductsByCategory(@Param("category") String category);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p.productId, p.productName, p.brand, p.imageURL, SUM(oc.quantity) AS totalPurchased " +
            "FROM OrderContains oc " +
            "JOIN Orders o ON oc.orderId = o.orderId " +
            "JOIN User u ON o.userId = u.userId " +
            "JOIN Product p ON oc.productId = p.productId " +
            "WHERE u.membershipType = :membershipType " +
            "GROUP BY p.productId, p.brand " +
            "ORDER BY totalPurchased DESC " +
            "Limit 3")
    List<Object[]> findTopProductsByMembership(@Param("membershipType") MembershipType membershipType);


}