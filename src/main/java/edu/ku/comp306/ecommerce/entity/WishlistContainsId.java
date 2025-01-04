package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
class WishlistContainsId implements Serializable {
    @Column(name = "WLID")
    private Integer wlId;
    @Column(name = "ProductID")
    private Integer productId;
}
