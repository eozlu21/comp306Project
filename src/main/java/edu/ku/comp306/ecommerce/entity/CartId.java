package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class CartId implements Serializable {
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "ProductID")
    private Integer productId;
}
