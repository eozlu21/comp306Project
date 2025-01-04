package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Desktop")
@Data
public class Desktop {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "PowerSupply", nullable = false)
    private Integer powerSupply;

    @Column(name = "RGB", nullable = false)
    private Boolean rgb;

    @Column(name = "NumberOfFans", nullable = false)
    private Integer numberOfFans;

    @Column(name = "CoolingSystem", length = 50, nullable = false)
    private String coolingSystem;
}
