package edu.ku.comp306.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReviewDTO {
    private String username;
    private Integer userId;
    private Integer productId;
    private Date reviewDate;
    private Integer rating;
    private String comment;
}
