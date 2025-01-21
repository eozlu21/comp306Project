package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.service.CartService;
import edu.ku.comp306.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final CartService cartService;
    public CartController(CartService cartService) {

        this.cartService = cartService;
    }
    @GetMapping("/cartCount")
    public int getCartItemCount(@RequestParam("userID") Integer userId) {
        return cartService.getItemCountForUser(userId);
    }
}
