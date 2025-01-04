package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
public class OrderContainsId implements Serializable {
    @Column(name = "OrderID")
    private Integer orderId;

    @Column(name = "ProductID")
    private Integer productId;
}
