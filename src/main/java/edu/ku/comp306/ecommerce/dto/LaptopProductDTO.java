package edu.ku.comp306.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LaptopProductDTO {
    private Integer laptopId;
    private String productName;
    private String resolution;
    private Double price;
}