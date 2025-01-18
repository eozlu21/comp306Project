package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Product;
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

    @Query(value = "SELECT * FROM Product ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Product> findRandomProducts();

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

    // Search products by name or brand (case-insensitive)
    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    // Sort products by price ascending
    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortByPriceAsc();

    // Sort products by price descending
    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> sortByPriceDesc();
}