package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Screen")
@Data
public class Screen {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "ScreenSize", length = 50, nullable = false)
    private String screenSize;

    @Column(name = "Resolution", length = 50, nullable = false)
    private String resolution;

    @Column(name = "Speaker", length = 50, nullable = false)
    private String speaker;

    @Column(name = "WifiConnectivity", nullable = false)
    private Boolean wifiConnectivity;
}
