package edu.ku.comp306.ecommerce.dto;

import edu.ku.comp306.ecommerce.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    private Product product;
    private Integer quantity; // Quantity of the product in the order

}
