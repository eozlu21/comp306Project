package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Computer")
@Data
public class Computer {

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "CPU", length = 50, nullable = false)
    private String cpu;

    @Column(name = "GPU", length = 50, nullable = false)
    private String gpu;

    @Column(name = "RAM", nullable = false)
    private Integer ram;

    @Column(name = "Storage", length = 50, nullable = false)
    private String storage;

    @Column(name = "OS", length = 50, nullable = false)
    private String os;
}
