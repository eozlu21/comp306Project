package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Camera")
@Data
public class Camera {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "Resolution", length = 50, nullable = false)
    private String resolution;

    // If "OpticalZoom" is actually a numeric level, store as Integer
    @Column(name = "OpticalZoom", nullable = false)
    private Integer opticalZoom;

    // Commonly "tinyint(1)" = boolean
    @Column(name = "Flash", nullable = false)
    private Boolean flash;

    @Column(name = "BatteryLife", nullable = false)
    private Integer batteryLife;
}