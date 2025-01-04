package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Laptop")
@Data
public class Laptop {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "FaceRecognition", nullable = false)
    private Boolean faceRecognition;

    @Column(name = "Webcam", nullable = false)
    private Boolean webcam;

    @Column(name = "Resolution", length = 50, nullable = false)
    private String resolution;

    @Column(name = "TouchScreen", nullable = false)
    private Boolean touchScreen;

    @Column(name = "FingerprintSensor", nullable = false)
    private Boolean fingerprintSensor;

    @Column(name = "ScreenSize", length = 50, nullable = false)
    private String screenSize;

    @Column(name = "BatteryCapacity", nullable = false)
    private Integer batteryCapacity;
}
