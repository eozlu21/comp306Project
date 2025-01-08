package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "OrderContains")
@IdClass(OrderContainsId.class)
@Data
public class OrderContains {
    @Id
    @Column(name = "OrderID")
    private Integer orderId;

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}
