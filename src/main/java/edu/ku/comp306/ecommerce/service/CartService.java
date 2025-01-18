package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.CartItemDto;
import edu.ku.comp306.ecommerce.dto.CartSummaryDto;
import edu.ku.comp306.ecommerce.entity.Cart;
import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.repository.CartRepository;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    /**
     * Retrieve all items in the cart for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of CartItemDto containing product and quantity details.
     */
    public List<CartItemDto> getCartItems(Integer userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return cartItems.stream()
                .map(cart -> {
                    Product product = productRepository.findById(cart.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + cart.getProductId()));
                    return new CartItemDto(product, cart.getQuantity());
                })
                .collect(Collectors.toList());
    }

    /**
     * Calculate the cart summary for a user.
     * 
     * @param userId The ID of the user.
     * @return A CartSummaryDto containing total items and total price.
     */
    public CartSummaryDto getCartSummary(Integer userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);

        int totalItems = cartItems.stream().mapToInt(Cart::getQuantity).sum();
        double totalPrice = cartItems.stream()
                .mapToDouble(cart -> {
                    Product product = productRepository.findById(cart.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found: " + cart.getProductId()));
                    return product.getPrice() * cart.getQuantity();
                })
                .sum();

        return new CartSummaryDto(totalItems, totalPrice);
    }

    /**
     * Remove a specific product from the user's cart.
     * 
     * @param userId    The ID of the user.
     * @param productId The ID of the product to remove.
     */
    public void removeProductFromCart(Integer userId, Integer productId) {
        cartRepository.deleteByUserIdAndProductId(userId, productId);
    }

    /**
     * Update the quantity of a specific product in the user's cart.
     * 
     * @param userId    The ID of the user.
     * @param productId The ID of the product to update.
     * @param quantity  The new quantity for the product.
     */
    public void updateProductQuantity(Integer userId, Integer productId, Integer quantity) {
        Cart cart = cartRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for Product ID: " + productId));

        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    public void addToCart(Integer userId, Integer productId, Integer quantity) {
        cartRepository.save(new Cart(userId, productId, quantity));
    }
}