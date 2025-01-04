package edu.ku.comp306.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "Reviewed")
@IdClass(ReviewedId.class)
@Data
public class Reviewed {

    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Id
    @Column(name = "ProductID")
    private Integer productId;

    @Column(name = "ReviewDate", nullable = false)
    private LocalDate reviewDate;

    @Column(name = "ReviewRating", nullable = false)
    private Integer reviewRating;

    @Column(name = "Comment", length = 500)
    private String comment;
}
