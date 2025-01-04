package edu.ku.comp306.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LaptopProductDTO {
    private Integer laptopId;
    private String resolution;
    private String productName;
    private Double price;
}