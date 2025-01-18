package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getRandomProducts() {
        return productRepository.findRandomProducts();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findProductsByCategory(category.toLowerCase());
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Search method for products
    public List<Product> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword);
    }

    public List<Product> sortProductsByPrice(String order) {
        if ("asc".equalsIgnoreCase(order)) {
            return productRepository.sortByPriceAsc();
        } else if ("desc".equalsIgnoreCase(order)) {
            return productRepository.sortByPriceDesc();
        }
        return productRepository.findAll();  // Default case
    }
}
