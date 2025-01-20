package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.*;
import edu.ku.comp306.ecommerce.repository.CameraRepository;
import edu.ku.comp306.ecommerce.repository.PhoneRepository;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import edu.ku.comp306.ecommerce.service.CartService;
import edu.ku.comp306.ecommerce.service.OrderService;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductDetailsController {

    private final ProductService productService;
    private final LaptopRepository laptopRepository;
    private final PhoneRepository phoneRepository;
    private final CameraRepository cameraRepository;
    private final ReviewedRepository reviewedRepository;
    private final CartService cartService;
    private final OrderService orderService;

    @GetMapping("/productDetails/{id}")
    public String getProductDetails(
            @PathVariable("id") Integer productId,
            @RequestParam("userID") Integer userId,
            Model model) {

        Product product = productService.getProductById(productId);
        if (product == null) {
            return "redirect:/homepage?userID=" + userId;
        }


        Laptop laptop = laptopRepository.findById(productId).orElse(null);
        Phone phone = phoneRepository.findById(productId).orElse(null);
        Camera camera = cameraRepository.findById(productId).orElse(null);
        boolean hasPurchased = orderService.hasPurchased(userId, productId);
        Double averageRating = reviewedRepository.getAverageRating(productId);
        if (averageRating == null) {
            averageRating = 0.0;
        }
        String formattedAvgRating = String.format("%.1f", averageRating);
        long ratedCount = reviewedRepository.getRatedCount(productId);

        model.addAttribute("product", product);
        model.addAttribute("laptop", laptop);
        model.addAttribute("phone", phone);
        model.addAttribute("camera", camera);
        model.addAttribute("userId", userId);
        model.addAttribute("hasPurchased", hasPurchased);
        model.addAttribute("averageRating", formattedAvgRating);
        model.addAttribute("ratedCount", ratedCount);


        List<UserReviewDTO> reviews = reviewedRepository.findReviewsForProduct(productId);
        model.addAttribute("reviews", reviews);

        return "productDetails";
    }

    @PostMapping("/addReview")
    public String addReview(
            @RequestParam("productId") Integer productId,
            @RequestParam("userId") Integer userId,
            @RequestParam("rating") Integer rating,
            @RequestParam(value = "comment", required = false) String comment
    ) {
        // Save the review using the repository
        reviewedRepository.save(new Reviewed(userId, productId, LocalDate.now(), rating, comment));
        return "redirect:/productDetails/" + productId + "?userID=" + userId;
    }

    @PostMapping("/addToCart")
    public String addToCart(
            @RequestParam("productId") Integer productId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("userId") Integer userId
    ) {
        cartService.addToCart(userId, productId, quantity);
        return "redirect:/productDetails/" + productId + "?userID=" + userId;
    }

    @GetMapping("/products/{category}")
    public String getProductsByCategory(@PathVariable("category") String category, Model model) {
        List<Product> products;

        switch (category.toLowerCase()) {
            case "laptop":
                products = laptopRepository.findAll()
                        .stream()
                        .map(laptop -> productService.getProductById(laptop.getProductId()))
                        .toList();
                break;

            case "phone":
                products = phoneRepository.findAll()
                        .stream()
                        .map(phone -> productService.getProductById(phone.getProductId()))
                        .toList();
                break;

            case "camera":
                products = cameraRepository.findAll()
                        .stream()
                        .map(camera -> productService.getProductById(camera.getProductId()))
                        .toList();
                break;

            default:
                products = productService.getAllProducts();
                break;
        }

        model.addAttribute("products", products);
        model.addAttribute("category", category);

        return "product-list";
    }
}
