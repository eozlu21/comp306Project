package edu.ku.comp306.ecommerce.repository;

import edu.ku.comp306.ecommerce.entity.Phone;
import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Query(value = """
    SELECT p.*
    FROM Phone ph
    JOIN Product p ON ph.productId = p.productId
    WHERE (:chargerType IS NULL OR ph.chargerType = :chargerType)
      AND (:batteryCapacityMah IS NULL OR ph.`BatteryCapacity(mAh)`= :batteryCapacityMah)
      AND (:screenSize IS NULL OR ph.screenSize = :screenSize)
      AND (:resolution IS NULL OR ph.resolution = :resolution)
      AND (:cameraResolution IS NULL OR ph.cameraResolution = :cameraResolution)
      AND (:processor IS NULL OR ph.processor = :processor)
      AND (:ram IS NULL OR ph.ram = :ram)
      AND (:fastCharging IS NULL OR ph.fastCharging = :fastCharging)
      AND (:fingerprintSensor IS NULL OR ph.fingerprintSensor = :fingerprintSensor)
      AND (:faceRecognition IS NULL OR ph.faceRecognition = :faceRecognition)
      AND (:networkCompatibility IS NULL OR ph.networkCompatibility = :networkCompatibility)
      AND (:dualSimCard IS NULL OR ph.dualSimCard = :dualSimCard)
      AND (:nfc IS NULL OR ph.nfc = :nfc)
""", nativeQuery = true)
    List<Product> filterPhones(
            @Param("chargerType") String chargerType,
            @Param("batteryCapacityMah") Integer batteryCapacityMah,
            @Param("screenSize") String screenSize,
            @Param("resolution") String resolution,
            @Param("cameraResolution") String cameraResolution,
            @Param("processor") String processor,
            @Param("ram") Integer ram,
            @Param("fastCharging") Boolean fastCharging,
            @Param("fingerprintSensor") Boolean fingerprintSensor,
            @Param("faceRecognition") Boolean faceRecognition,
            @Param("networkCompatibility") String networkCompatibility,
            @Param("dualSimCard") Boolean dualSimCard,
            @Param("nfc") Boolean nfc
    );
}