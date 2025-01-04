package edu.ku.comp306.ecommerce.entity;

import edu.ku.comp306.ecommerce.enums.EarphoneType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Earphone")
@Data
public class Earphone {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    /**
     * If "Connectivity" tinyint(1) means "is it wireless?"
     * you can map it to boolean. Adjust naming to be more descriptive if needed.
     */
    @Column(name = "Connectivity", nullable = false)
    private Boolean connectivityWireless;

    @Enumerated(EnumType.STRING)
    @Column(name = "Type", nullable = false)
    private EarphoneType type;

    @Column(name = "NoiseCancellation", nullable = false)
    private Boolean noiseCancellation;

    @Column(name = "BatteryLife", nullable = false)
    private Integer batteryLife;
}
