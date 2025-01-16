package edu.ku.comp306.ecommerce.entity;

import edu.ku.comp306.ecommerce.converter.ProductColorConverter;
import edu.ku.comp306.ecommerce.enums.ProductColor;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "ProductName", length = 100, nullable = false)
    private String productName;

    @Column(name = "Price", nullable = false)
    private Float price;

    @Convert(converter = ProductColorConverter.class)
    @Column(name = "Color", nullable = false)
    private ProductColor color;

    @Column(name = "Brand", length = 50, nullable = false)
    private String brand;

    @Column(name = "Weight", nullable = false)
    private Float weight;

    @Column(name = "Dimension", length = 50, nullable = false)
    private String dimension;

    @Column(name = "WarrantyDurationYears", nullable = false)
    private Integer warrantyDurationYears;

    /**
     * MySQL YEAR(4) can be treated as an integer or a short.
     * Alternatively, we could use a java.time object, but typically
     * year is stored as int or short in JPA for simplicity.
     */
    @Column(name = "ReleaseDate", nullable = false)
    private Integer releaseDate;

    @Column(name = "ImageURL", length = 200)
    private String imageURL;
}
