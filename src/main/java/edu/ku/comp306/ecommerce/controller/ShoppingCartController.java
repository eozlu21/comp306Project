package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class ShoppingCartController {

    private final CartService cartService;

    public ShoppingCartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Display the Shopping Cart Page for a specific user.
     *
     * @param userId The ID of the logged-in user.
     * @param model  The model to pass data to the Thymeleaf template.
     * @return The shopping cart HTML view.
     */
    @GetMapping("/cart/{userId}")
    public String showShoppingCart(@PathVariable("userId") Integer userId, Model model) {
        // Retrieve all cart items for the user
        var cart = cartService.getCart(userId);

        // Add cart data to the model
        model.addAttribute("cartItems", cart.getCartItems());
        model.addAttribute("cart", cart);
        model.addAttribute("userId", userId);

        return "shoppingCart";
    }

    /**
     * Remove a product from the shopping cart for a specific user.
     *
     * @param userId    The ID of the logged-in user.
     * @param productId The ID of the product to remove.
     * @return Redirect to the shopping cart page.
     */
    @PostMapping("/cart/remove/{userId}/{productId}")
    public String removeProductFromCart(@PathVariable("userId") Integer userId,
                                        @PathVariable("productId") Integer productId) {
        cartService.removeProductFromCart(userId, productId);
        return "redirect:/cart/" + userId;
    }

    /**
     * Update the quantity of a product in the shopping cart for a specific user.
     *
     * @param userId    The ID of the logged-in user.
     * @param productId The ID of the product to update.
     * @param quantity  The new quantity for the product.
     * @return Redirect to the shopping cart page.
     */
    @PostMapping("cart/update/{userId}/{productId}")
    public String updateProductQuantity(@PathVariable("userId") Integer userId,
                                        @PathVariable("productId") Integer productId,
                                        @RequestParam("quantity") Integer quantity) {
        cartService.updateProductQuantity(userId, productId, quantity);
        return "redirect:/cart/" + userId;
    }

    @PostMapping("cart/checkout/{userId}")
    public String checkout(@PathVariable("userId") Integer userId) {
        // todo: nereye atsın karar verelim ona göre ayarlarız
        return "redirect:/profile/" + userId;
    }
}