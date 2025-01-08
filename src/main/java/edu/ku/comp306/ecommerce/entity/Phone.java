package edu.ku.comp306.ecommerce.entity;

import edu.ku.comp306.ecommerce.enums.ChargerType;
import edu.ku.comp306.ecommerce.enums.NetworkCompatibility;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Phone")
@Data
public class Phone {
    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ChargerType", nullable = false)
    private ChargerType chargerType;

    @Column(name = "BatteryCapacity(mAh)", nullable = false)
    private Integer batteryCapacityMah;

    @Column(name = "ScreenSize", length = 50, nullable = false)
    private String screenSize;

    @Column(name = "Resolution", length = 50, nullable = false)
    private String resolution;

    @Column(name = "CameraResolution", length = 50, nullable = false)
    private String cameraResolution;

    @Column(name = "Processor", length = 50, nullable = false)
    private String processor;

    @Column(name = "RAM", nullable = false)
    private Integer ram;

    @Column(name = "FastCharging", nullable = false)
    private Boolean fastCharging;

    @Column(name = "FingerprintSensor", nullable = false)
    private Boolean fingerprintSensor;

    @Column(name = "FaceRecognition", nullable = false)
    private Boolean faceRecognition;

    @Enumerated(EnumType.STRING)
    @Column(name = "NetworkCompatibility", nullable = false)
    private NetworkCompatibility networkCompatibility;

    @Column(name = "DualSIMCard", nullable = false)
    private Boolean dualSimCard;

    @Column(name = "NFC", nullable = false)
    private Boolean nfc;
}
