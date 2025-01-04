package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "Shipment")
@Data
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ShipmentID")
    private Integer shipmentId;

    @Column(name = "ShipmentDate", nullable = false)
    private LocalDate shipmentDate;

    @Column(name = "Status", length = 50, nullable = false)
    private String status;

    @Column(name = "EstDeliveryDate", nullable = false)
    private LocalDate estDeliveryDate;

    @Column(name = "OrderID", nullable = false)
    private Integer orderId;
}
