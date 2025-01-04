package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Seller")
@Data
public class Seller {

    @Id
    @Column(name = "UserID")
    private Integer userId;

}
