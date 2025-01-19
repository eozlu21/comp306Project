package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.CartDto;
import edu.ku.comp306.ecommerce.dto.CartItemDto;
import edu.ku.comp306.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartDto getCart(Integer userId) {
        var cartItems = cartRepository.findByUserId(userId);
        var cartItemDtoList = cartItems.stream().map(e -> new CartItemDto(productService.getProductById(e.getProductId()), e.getQuantity())).toList();
        return new CartDto(cartItemDtoList);
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
        // do wee need to check if does not exist??
        cartRepository.editQuantity(userId, productId, quantity);
    }

    public void addToCart(Integer userId, Integer productId, Integer quantity) {
        cartRepository.saveCart(userId, productId, quantity);
    }
}
