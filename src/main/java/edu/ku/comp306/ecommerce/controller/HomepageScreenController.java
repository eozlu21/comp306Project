package edu.ku.comp306.ecommerce.controller;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.enums.MembershipType;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import edu.ku.comp306.ecommerce.service.ProductService;
import edu.ku.comp306.ecommerce.service.UserService;
import edu.ku.comp306.ecommerce.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomepageScreenController {

    private final ProductService productService;
    private final ReviewedRepository reviewedRepository;
    private final UserService userService;
    private final CartService cartService;

    public HomepageScreenController(ProductService productService, ReviewedRepository reviewedRepository, UserService userService, CartService cartService) {
        this.productService = productService;
        this.reviewedRepository = reviewedRepository;
        this.userService = userService;
        this.cartService = cartService;
    }

    @GetMapping("/homepage")
    public String getHomePage(@RequestParam("userID") Integer userId, Model model) {
        // Fetch popular products
        List<Product> products = productService.getPopularProducts();

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
        model.addAttribute("productRatings", productRatings);
        model.addAttribute("productReviews", productReviews);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("cartItemCount", cartItemCount);

        MembershipType membershipType = userService.getMembershipTypeById(userId);
        List<Map<String, Object>> suggestedProducts = productService.getTopProductsForMembership(membershipType);
        model.addAttribute("suggestedProducts", suggestedProducts);

        return "homepageScreen";
    }

}
