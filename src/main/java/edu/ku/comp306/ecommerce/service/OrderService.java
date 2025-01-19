package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;


    public boolean hasPurchased(Integer userId, Integer productId) {
        long orderCount = ordersRepository.getOrderedCount(productId, userId);
        return orderCount > 0;
    }
}
