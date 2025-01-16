package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Cart")
@IdClass(CartId.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
