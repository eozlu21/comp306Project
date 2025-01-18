package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.CartItemDto;
import edu.ku.comp306.ecommerce.dto.CartSummaryDto;
import edu.ku.comp306.ecommerce.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ShoppingCartController {

    private final CartService cartService;

    public ShoppingCartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Display the Shopping Cart Page.
     *
     * @param userId The ID of the logged-in user.
     * @param model  The model to pass data to the Thymeleaf template.
     * @return The shopping cart HTML view.
     */
    @GetMapping("/cart")
    public String showShoppingCart(@RequestParam(value = "userId", defaultValue = "11") Integer userId, Model model) {
        // Retrieve all cart items for the user
        List<CartItemDto> cartItems = cartService.getCartItems(userId);

        // Calculate the cart summary (total items and price)
        CartSummaryDto cartSummary = cartService.getCartSummary(userId);

        // Add cart data to the model
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartSummary", cartSummary);

        return "shoppingCart";
    }

    /**
     * Remove a product from the shopping cart.
     *
     * @param userId    The ID of the logged-in user.
     * @param productId The ID of the product to remove.
     * @return Redirect to the shopping cart page.
     */
    @PostMapping("/cart/remove/{productId}")
    public String removeProductFromCart(@RequestParam("userId") Integer userId,
                                         @PathVariable("productId") Integer productId) {
        cartService.removeProductFromCart(userId, productId);
        return "redirect:/cart?userId=" + userId;
    }

    /**
     * Update the quantity of a product in the shopping cart.
     *
     * @param userId    The ID of the logged-in user.
     * @param productId The ID of the product to update.
     * @param quantity  The new quantity for the product.
     * @return Redirect to the shopping cart page.
     */
    @PostMapping("/cart/update/{productId}")
    public String updateProductQuantity(@RequestParam("userId") Integer userId,
                                        @PathVariable("productId") Integer productId,
                                        @RequestParam("quantity") Integer quantity) {
        cartService.updateProductQuantity(userId, productId, quantity);
        return "redirect:/cart?userId=" + userId;
    }
}