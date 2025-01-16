package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.*;
import edu.ku.comp306.ecommerce.repository.CameraRepository;
import edu.ku.comp306.ecommerce.repository.PhoneRepository;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import edu.ku.comp306.ecommerce.service.CartService;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductDetailsController {

    private final ProductService productService;
    private final LaptopRepository laptopRepository;
    private final PhoneRepository phoneRepository;
    private final CameraRepository cameraRepository;
    private final ReviewedRepository reviewedRepository;
    private final CartService cartService;

    @GetMapping("/productDetails/{id}")
    public String getProductDetails(@PathVariable("id") Integer productId, Model model) {
        // 1) Get the base Product
        Product product = productService.getProductById(productId);
        if (product == null) {
            // handle not found
            return "redirect:/homepage";
        }

        // 2) Check if it’s a laptop
        //    If in your DB, a laptop record only exists if productId is in the Laptop table:
        Laptop laptop = laptopRepository.findById(productId).orElse(null);
        // similarly for phone, camera, etc.
        Phone phone = phoneRepository.findById(productId).orElse(null);
        Camera camera = cameraRepository.findById(productId).orElse(null);
        // 3) Add them to the model
        model.addAttribute("product", product);
        model.addAttribute("laptop", laptop);
        model.addAttribute("phone", phone);
        model.addAttribute("camera", camera);

        // You can also fetch reviews if you have a separate table for that
        List<UserReviewDTO> reviews = reviewedRepository.findReviewsForProduct(productId);
        model.addAttribute("reviews", reviews);

        return "productDetails";
    }

    // -----------------------------------------------------------
    // (Optional) Add-to-Cart handling directly here or in a CartController
    // -----------------------------------------------------------
    @PostMapping("/addToCart")
    public String addToCart(
            @RequestParam("productId") Integer productId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("userId") Integer userId
    ) {
        // your service logic to add (or update) the product in the Cart
        cartService.addToCart(userId, productId, quantity);

        return "redirect:/productDetails/" + productId;
    }
}
