package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.entity.Buyer;
import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductRepository productRepository;

    @GetMapping("/")
    public String getHomePage(Model model) {
        // Fetch all products from the database
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "home";
    }


}