package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.UserReviewDTO;
import edu.ku.comp306.ecommerce.entity.Product;

import edu.ku.comp306.ecommerce.repository.CameraRepository;
import edu.ku.comp306.ecommerce.repository.LaptopRepository;
import edu.ku.comp306.ecommerce.repository.PhoneRepository;

import edu.ku.comp306.ecommerce.enums.MembershipType;

import edu.ku.comp306.ecommerce.repository.ProductRepository;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final PhoneRepository phoneRepository;
    private final CameraRepository cameraRepository;
    private final LaptopRepository laptopRepository;

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

    public List<Product> filterProductsByCategory(String category, Map<String, String> filters) {
        switch (category.toLowerCase()) {
            case "camera":
                return cameraRepository.filterCameras(
                        parseString(filters.get("resolution")),
                        parseInteger(filters.get("opticalZoom")),
                        parseBoolean(filters.get("flash")),
                        parseInteger(filters.get("batteryLife"))
                );
            case "laptop":
                return laptopRepository.filterLaptops(
                        parseString(filters.get("cpu")),
                        parseString(filters.get("gpu")),
                        parseInteger(filters.get("ram")),
                        parseInteger(filters.get("storage")),
                        parseString(filters.get("os")),
                        parseBoolean(filters.get("faceRecognition")),
                        parseBoolean(filters.get("webcam")),
                        parseString(filters.get("resolution")),
                        parseBoolean(filters.get("touchScreen")),
                        parseBoolean(filters.get("fingerprintSensor")),
                        parseString(filters.get("screenSize")),
                        parseInteger(filters.get("batteryCapacity"))
                );
            case "phone":
                return phoneRepository.filterPhones(
                        parseString(filters.get("chargerType")),
                        parseInteger(filters.get("batteryCapacityMah")),
                        parseString(filters.get("screenSize")),
                        parseString(filters.get("resolution")),
                        parseString(filters.get("cameraResolution")),
                        parseString(filters.get("processor")),
                        parseInteger(filters.get("ram")),
                        parseBoolean(filters.get("fastCharging")),
                        parseBoolean(filters.get("fingerprintSensor")),
                        parseBoolean(filters.get("faceRecognition")),
                        parseString(filters.get("networkCompatibility")),
                        parseBoolean(filters.get("dualSimCard")),
                        parseBoolean(filters.get("nfc"))
                );


            default:
                return List.of(); // Return empty list for unsupported categories
        }
    }

    /**
     * Helper method to parse a string to an integer, returning null for empty or invalid input.
     */
    private Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Integer.valueOf(value);
    }

    private Boolean parseBoolean(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
    return boolval(value);
    }

    private Boolean boolval(String val){
        return val.equals("1");
    }

    private String parseString(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public List<Map<String, Object>> getTopProductsForMembership(MembershipType membershipType) {
        // Fetch data from the repository
        List<Object[]> results = productRepository.findTopProductsByMembership(membershipType);

        // Map results into a key-value structure
        return results.stream().map(row -> {
            Map<String, Object> productData = new HashMap<>();
            productData.put("productId", row[0]);
            productData.put("productName", row[1]); // Product ID
            productData.put("brand", row[2]);    // Brand
            productData.put("totalPurchased", row[4]); // Total Purchased Quantity
            productData.put("imageURL", row[3]);
            return productData;
        }).collect(Collectors.toList());
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
