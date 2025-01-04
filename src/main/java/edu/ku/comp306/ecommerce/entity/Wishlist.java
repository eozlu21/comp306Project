package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Wishlist")
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WLID")
    private Integer wlId;

    @Column(name = "WLName", length = 15, nullable = false)
    private String wlName;

    @Column(name = "UserID", nullable = false)
    private Integer userId;
}
