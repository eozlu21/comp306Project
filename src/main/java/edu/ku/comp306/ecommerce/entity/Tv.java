package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Tv")
@Data
public class Tv {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "SmartTV", nullable = false)
    private Boolean smartTv;
}
