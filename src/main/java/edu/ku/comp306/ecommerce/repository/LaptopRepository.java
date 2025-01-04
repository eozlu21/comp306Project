package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.dto.LaptopProductDTO;
import edu.ku.comp306.ecommerce.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
    // Join query for Laptop and Product
    @Query(value = """
            SELECT
                l.ProductID AS laptopId,
                l.Resolution AS resolution,
                p.ProductName AS productName,
                p.Price AS price
            FROM Laptop l
            INNER JOIN Product p ON l.ProductID = p.ProductID
            WHERE l.Resolution = ?1
            """, nativeQuery = true)
    List<LaptopProductDTO> findLaptopWithProductDetailsByResolution(String resolution);
}