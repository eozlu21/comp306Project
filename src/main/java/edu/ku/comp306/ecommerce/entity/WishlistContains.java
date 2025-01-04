package edu.ku.comp306.ecommerce.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Entity
@Table(name = "WishlistContains")
@IdClass(WishlistContainsId.class)
@Data
public class WishlistContains {

    @Id
    @Column(name = "WLID")
    private Integer wlId;

    @Id
    @Column(name = "ProductID")
    private Integer productId;
}
