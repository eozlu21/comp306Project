package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getRandomProducts() {
        return productRepository.findRandomProducts();
    }
}
