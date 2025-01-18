package edu.ku.comp306.ecommerce.controller;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HomepageScreenController {

    private final ProductService productService;

    public HomepageScreenController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/homepage")
    public String getHomePage(Model model) {
        List<Product> products = productService.getRandomProducts(); // Fetch random 5 products
        model.addAttribute("products", products);
        return "homepageScreen";
    }
}
