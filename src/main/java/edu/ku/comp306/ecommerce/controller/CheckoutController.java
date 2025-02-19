package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.service.CartService;
import edu.ku.comp306.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    private final OrderService orderService;
    private final CartService cartService;

    public CheckoutController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    /**
     * Display the Checkout Page for the recent order.
     *
     * @param userId  The ID of the logged-in user.
     * @param orderId The ID of the recent order.
     * @param model   The model to pass data to the Thymeleaf template.
     * @return The checkout HTML view.
     */
    @GetMapping("/checkout")
    public String showCheckoutPage(@RequestParam("userID") Integer userId,
                                   @RequestParam("orderID") Integer orderId,
                                   Model model) {
        // Retrieve the details of the recent order
        cartService.deleteCart(userId);
        var orderDTO = orderService.getOrderDetails(userId, orderId);
        
        model.addAttribute("order", orderDTO);
        model.addAttribute("userId", userId);

        return "checkout";
    }
}