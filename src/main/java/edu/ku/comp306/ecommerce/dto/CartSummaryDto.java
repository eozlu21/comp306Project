package edu.ku.comp306.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSummaryDto {
    private Integer totalItems; // Total number of items in the cart
    private Double totalPrice;  // Total price of all items in the cart
}