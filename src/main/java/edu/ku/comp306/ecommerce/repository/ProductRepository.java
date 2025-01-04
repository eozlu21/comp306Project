package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Find products by brand
    List<Product> findByBrand(String brand);

    // Find products within a price range
    @Query(value = "SELECT * FROM Product WHERE Price BETWEEN ?1 AND ?2", nativeQuery = true)
    List<Product> findByPriceRange(Float minPrice, Float maxPrice);

}