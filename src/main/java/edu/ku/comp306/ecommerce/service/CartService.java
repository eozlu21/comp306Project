package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public void addToCart(Integer userId, Integer productId, Integer quantity) {
        cartRepository.save(userId, productId, quantity);
    }
}
