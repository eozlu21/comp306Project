package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.enums.MembershipType;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
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
    public List<Map<String, Object>> getTopProductsForMembership(MembershipType membershipType) {
        // Fetch data from the repository
        List<Object[]> results = productRepository.findTopProductsByMembership(membershipType);

        // Map results into a key-value structure
        return results.stream().map(row -> {
            Map<String, Object> productData = new HashMap<>();
            productData.put("productId", row[0]); // Product ID
            productData.put("brand", row[1]);    // Brand
            productData.put("totalPurchased", row[3]); // Total Purchased Quantity
            productData.put("imageURL", row[2]);
            return productData;
        }).collect(Collectors.toList());
    }

}
