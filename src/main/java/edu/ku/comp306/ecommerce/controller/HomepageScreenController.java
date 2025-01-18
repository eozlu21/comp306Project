package edu.ku.comp306.ecommerce.controller;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomepageScreenController {

    private final ProductService productService;

    public HomepageScreenController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/homepage")
    public String getHomePage(@RequestParam("userID") Integer userId, Model model) {
        List<Product> products = productService.getRandomProducts(); // Fetch random products
        model.addAttribute("products", products);
        model.addAttribute("userId", userId); // Pass userID to the view
        return "homepageScreen";
    }
}
