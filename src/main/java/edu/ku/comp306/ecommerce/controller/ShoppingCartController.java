package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.service.CartService;
import edu.ku.comp306.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
public class ShoppingCartController {

    private final CartService cartService;
    private final OrderService orderService;

    public ShoppingCartController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    /**
     * Display the Shopping Cart Page for a specific user.
     *
     * @param userId The ID of the logged-in user.
     * @param model  The model to pass data to the Thymeleaf template.
     * @return The shopping cart HTML view.
     */
    @GetMapping("/cart")
    public String showShoppingCart(@RequestParam("userID") Integer userId, Model model) {
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
    @PostMapping("/cart/remove/{userID}/{productId}")
    public String removeProductFromCart(@PathVariable("userID") Integer userId,
                                        @PathVariable("productId") Integer productId) {
        cartService.removeProductFromCart(userId, productId);
        return "redirect:/cart?userID=" + userId;
    }

    /**
     * Update the quantity of a product in the shopping cart for a specific user.
     *
     * @param userId    The ID of the logged-in user.
     * @param productId The ID of the product to update.
     * @param quantity  The new quantity for the product.
     * @return Redirect to the shopping cart page.
     */
    @PostMapping("cart/update/{userID}/{productId}")
    public String updateProductQuantity(@PathVariable("userID") Integer userId,
                                        @PathVariable("productId") Integer productId,
                                        @RequestParam("quantity") Integer quantity) {
        cartService.updateProductQuantity(userId, productId, quantity);
        return "redirect:/cart?userID=" + userId;
    }

    @PostMapping("cart/checkout")
    public String checkout(@RequestParam("userID") Integer userId) {
        if (cartService.getCart(userId).getCartItems().isEmpty()) {
            return "redirect:/cart?userID=" + userId;
        }
        var orderID = orderService.createOrderId(userId, LocalDate.now());
        cartService.order(userId, orderID);
        return "redirect:/checkout?userID=" + userId + "&orderID=" + orderID;
    }
}