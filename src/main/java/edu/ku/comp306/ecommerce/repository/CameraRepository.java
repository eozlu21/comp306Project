package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Camera;
import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {
    @Query(value = """
    SELECT p.* 
    FROM Camera c 
    JOIN Product p ON c.productId = p.productId 
    WHERE (:resolution IS NULL OR c.resolution = :resolution)
      AND (:opticalZoom IS NULL OR c.opticalZoom = :opticalZoom)
      AND (:flash IS NULL OR c.flash = :flash)
      AND (:batteryLife IS NULL OR c.batteryLife = :batteryLife)
""", nativeQuery = true)
    List<Product> filterCameras(
            @Param("resolution") String resolution,
            @Param("opticalZoom") Integer opticalZoom,
            @Param("flash") Boolean flash,
            @Param("batteryLife") Integer batteryLife
    );
}
