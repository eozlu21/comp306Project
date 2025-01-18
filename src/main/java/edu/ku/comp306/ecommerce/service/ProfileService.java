package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.OrdersWithProductsDTO;
import edu.ku.comp306.ecommerce.entity.Orders;
import edu.ku.comp306.ecommerce.entity.OrderContains;
import edu.ku.comp306.ecommerce.entity.Reviewed;
import edu.ku.comp306.ecommerce.entity.User;
import edu.ku.comp306.ecommerce.repository.OrderContainsRepository;
import edu.ku.comp306.ecommerce.repository.OrdersRepository;
import edu.ku.comp306.ecommerce.repository.ReviewedRepository;
import edu.ku.comp306.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ReviewedRepository reviewedRepository;
    private final OrdersRepository ordersRepository;
    private final OrderContainsRepository orderContainsRepository;

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<Reviewed> getUserReviews(Integer userId) {
        return reviewedRepository.findByUserId(userId);
    }

    public List<OrdersWithProductsDTO> getUserOrdersWithProducts(Integer userId) {
        List<Orders> orders = ordersRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            List<OrderContains> products = orderContainsRepository.findByOrderId(order.getOrderId());
            return new OrdersWithProductsDTO(order, products);
        }).collect(Collectors.toList());
    }
    public void updateUserProfile(User user) {
        userRepository.save(user); // Save updated user details
    }
}
