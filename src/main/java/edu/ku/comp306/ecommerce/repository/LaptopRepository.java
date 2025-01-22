package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.dto.LaptopProductDTO;
import edu.ku.comp306.ecommerce.entity.Laptop;
import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Query(value = """
    SELECT p.* 
    FROM Laptop l 
    JOIN Product p ON l.productId = p.productId 
    WHERE (:cpu IS NULL OR l.cpu = :cpu) 
      AND (:gpu IS NULL OR l.gpu = :gpu)
      AND (:ram IS NULL OR l.ram = :ram) 
      AND (:storage IS NULL OR l.storage = :storage)
      AND (:os IS NULL OR l.os = :os)
      AND (:faceRecognition IS NULL OR l.faceRecognition = :faceRecognition)
      AND (:webcam IS NULL OR l.webcam = :webcam)
      AND (:resolution IS NULL OR l.resolution = :resolution)
      AND (:touchScreen IS NULL OR l.touchScreen = :touchScreen)
      AND (:fingerprintSensor IS NULL OR l.fingerprintSensor = :fingerprintSensor)
      AND (:screenSize IS NULL OR l.screenSize = :screenSize)
      AND (:batteryCapacity IS NULL OR l.batteryCapacity = :batteryCapacity)
""", nativeQuery = true)
    List<Product> filterLaptops(
            @Param("cpu") String cpu,
            @Param("gpu") String gpu,
            @Param("ram") Integer ram,
            @Param("storage") Integer storage,
            @Param("os") String os,
            @Param("faceRecognition") Boolean faceRecognition,
            @Param("webcam") Boolean webcam,
            @Param("resolution") String resolution,
            @Param("touchScreen") Boolean touchScreen,
            @Param("fingerprintSensor") Boolean fingerprintSensor,
            @Param("screenSize") String screenSize,
            @Param("batteryCapacity") Integer batteryCapacity
    );
}