package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tablet")
@Data
public class Tablet {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "KeyboardCompatibility", nullable = false)
    private Boolean keyboardCompatibility;

    @Column(name = "SIMCardCompatibility", nullable = false)
    private Boolean simCardCompatibility;
}
