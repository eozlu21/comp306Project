package edu.ku.comp306.ecommerce.entity;

import edu.ku.comp306.ecommerce.enums.MembershipType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Buyer")
@Data
public class Buyer {

    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "MembershipType", nullable = false)
    private MembershipType membershipType;
}