package edu.ku.comp306.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private List<CartItemDto> cartItems; // Quantity of the product in the cart

    public int getNumberOfItems() {
        return cartItems.stream().mapToInt(CartItemDto::getQuantity).sum();
    }

    public double getTotalPrice() {
        return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }
}
