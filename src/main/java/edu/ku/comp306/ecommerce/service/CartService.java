package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.CartDTO;
import edu.ku.comp306.ecommerce.dto.CartItemDTO;
import edu.ku.comp306.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    private final OrderService orderService;

    public CartDTO getCart(Integer userId) {
        var cartItems = cartRepository.findByUserId(userId);
        var cartItemDtoList = cartItems.stream().map(e -> new CartItemDTO(productService.getProductById(e.getProductId()), e.getQuantity())).toList();
        return new CartDTO(cartItemDtoList);
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
        cartRepository.editQuantity(userId, productId, quantity);
    }

    public void addToCart(Integer userId, Integer productId, Integer quantity) {
        var newQuantity = quantity;
        if (cartRepository.findByUserIdAndProductId(userId, productId).isPresent()){
            newQuantity = cartRepository.findByUserIdAndProductId(userId, productId).get().getQuantity() + quantity;
            cartRepository.editQuantity(userId, productId, newQuantity);
        } else
            cartRepository.saveCart(userId, productId, quantity);
    }


    public void order(Integer userId, Integer orderId) {
        var cart = getCart(userId);
        orderService.createOrderContains(userId, orderId, cart);
    }

    public void deleteCart(Integer userId) {
        cartRepository.deleteByUserId(userId);
    }
}
