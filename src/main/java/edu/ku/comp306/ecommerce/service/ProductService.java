package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewedRepository reviewedRepository;

    public List<Product> getPopularProducts() {
        return productRepository.findPopularProducts();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findProductsByCategory(category);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Map<Integer, Double> fetchProductRatings(List<Product> products) {
        // Fetch average ratings for each product
        Map<Integer, Double> productRatings = products.stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        product -> {
                            Double avgRating = reviewedRepository.getAverageRating(product.getProductId());
                            return avgRating != null ? avgRating : 0.0;
                        }
                ));
        return productRatings;
    }

    public Map<Integer, List<UserReviewDTO>> fetchProductReviews(List<Product> products) {
        // Fetch reviews for each product
        Map<Integer, List<UserReviewDTO>> productReviews = products.stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        product -> reviewedRepository.findReviewsForProduct(product.getProductId())
                                .stream()
                                .limit(2) // Limit the list to 2 reviews
                                .collect(Collectors.toList())
                ));
        return productReviews;
    }
}
