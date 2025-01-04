package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
class ReviewedId implements Serializable {
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "ProductID")
    private Integer productId;
}
