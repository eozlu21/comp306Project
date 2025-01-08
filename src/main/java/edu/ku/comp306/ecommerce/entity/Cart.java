package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Cart")
@IdClass(CartId.class)
@Data
public class Cart {
    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;
}
