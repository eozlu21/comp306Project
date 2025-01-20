package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.type.descriptor.java.LocalDateTimeJavaType;

import java.time.LocalDate;

@Entity
@Table(name = "Orders")
@Data
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderID")
    private Integer orderId;

    @Column(name = "UserID", nullable = false)
    private Integer userId;

    @Column(name = "OrderDate", nullable = false)
    private LocalDate orderDate;
}