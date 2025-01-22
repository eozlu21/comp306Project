package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.*;
import edu.ku.comp306.ecommerce.repository.CameraRepository;
import edu.ku.comp306.ecommerce.repository.PhoneRepository;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import edu.ku.comp306.ecommerce.service.CartService;
import edu.ku.comp306.ecommerce.service.OrderService;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.service.UserService;
import edu.ku.comp306.ecommerce.repository.LaptopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final UserService userService;

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
        String userName = userService.getUserNameById(userId);
        int cartItemCount = cartService.getItemCountForUser(userId);

        model.addAttribute("product", product);
        model.addAttribute("laptop", laptop);
        model.addAttribute("phone", phone);
        model.addAttribute("camera", camera);
        model.addAttribute("userId", userId);
        model.addAttribute("hasPurchased", hasPurchased);
        model.addAttribute("averageRating", formattedAvgRating);
        model.addAttribute("ratedCount", ratedCount);
        model.addAttribute("userName", userName);
        model.addAttribute("cartItemCount", cartItemCount);


        List<UserReviewDTO> reviews = reviewedRepository.findReviewsForProduct(productId);
        model.addAttribute("reviews", reviews);

        return "product-details";
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
        // Return the new Thymeleaf template "cart-success" instead of a redirect
        return "cart-success";
    }

    @GetMapping("/products/{category}")
    public String getProductsByCategory(
            @PathVariable("category") String category,
            @RequestParam("userID") Integer userId,
            Model model) {

        // Fetch products for the specified category
        List<Product> products = productService.getProductsByCategory(category);

        // Fetch average ratings for each product
        Map<Integer, Double> productRatings = products.stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        product -> {
                            Double avgRating = reviewedRepository.getAverageRating(product.getProductId());
                            return avgRating != null ? avgRating : 0.0;
                        }
                ));

        // Fetch reviews for each product
        Map<Integer, List<UserReviewDTO>> productReviews = products.stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        product -> reviewedRepository.findReviewsForProduct(product.getProductId())
                                .stream()
                                .limit(2) // Limit the list to 2 reviews
                                .collect(Collectors.toList())
                ));
        String userName = userService.getUserNameById(userId);
        int cartItemCount = cartService.getItemCountForUser(userId);
        // Add data to the model
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("userId", userId);
        model.addAttribute("productRatings", productRatings);
        model.addAttribute("productReviews", productReviews);
        model.addAttribute("userName", userName);
        model.addAttribute("cartItemCount", cartItemCount);

        return "product-list";
    }

    @GetMapping("/search")
    public String searchProducts(
            @RequestParam("keyword") String keyword,
            @RequestParam("userId") Integer userId,
            Model model) {

        // Fetch products based on the search keyword
        List<Product> products = productService.searchProducts(keyword);

        // Fetch average ratings and reviews for the searched products
        Map<Integer, Double> productRatings = products.stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        product -> {
                            Double avgRating = reviewedRepository.getAverageRating(product.getProductId());
                            return avgRating != null ? avgRating : 0.0;
                        }
                ));

        Map<Integer, List<UserReviewDTO>> productReviews = products.stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        product -> reviewedRepository.findReviewsForProduct(product.getProductId())
                                .stream()
                                .limit(2) // Limit the list to 2 reviews
                                .collect(Collectors.toList())
                ));

        // Add data to the model
        model.addAttribute("products", products);
        model.addAttribute("productRatings", productRatings);
        model.addAttribute("productReviews", productReviews);
        model.addAttribute("keyword", keyword);
        model.addAttribute("userId", userId);

        return "search-results";
    }
}
