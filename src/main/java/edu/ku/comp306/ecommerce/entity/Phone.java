package edu.ku.comp306.ecommerce.entity;

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
    @Column(name = "NetworkCompatibility", nullable = false)
    private NetworkCompatibility networkCompatibility;

    @Column(name = "DualSIMCard", nullable = false)
    private Boolean dualSimCard;

    @Column(name = "NFC", nullable = false)
    private Boolean nfc;
}
