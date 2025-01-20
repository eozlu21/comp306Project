package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.OrderDTO;
import edu.ku.comp306.ecommerce.dto.OrderItemDto;
import edu.ku.comp306.ecommerce.entity.Orders;
import edu.ku.comp306.ecommerce.entity.OrderContains;
import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.entity.User;
import edu.ku.comp306.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final OrderContainsRepository orderContainsRepository;
    private final ProductRepository productRepository;

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<OrderDTO> getUserOrdersWithProducts(Integer userId) {
        List<Orders> orders = ordersRepository.findByUserId(userId);

        return orders.stream().map(order -> {
            // Retrieve the list of OrderContains for the given order
            List<OrderContains> orderContainsList = orderContainsRepository.findByOrderId(order.getOrderId());

            // Map each OrderContains instance to an OrderItemDto
            List<OrderItemDto> orderItems = orderContainsList.stream().map(orderContains -> {
                // Assuming ProductRepository is injected into the service
                Product product = productRepository.findById(orderContains.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                return new OrderItemDto(product, orderContains.getQuantity());
            }).collect(Collectors.toList());

            // Create and return the OrderDTO for this order
            return new OrderDTO(order.getOrderId(), orderItems, order.getOrderDate());
        }).collect(Collectors.toList());
    }
    public void updateUserProfile(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        // Retain the existing password if the new password is blank
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existingUser.getPassword());
        }
        userRepository.save(updatedUser);
    }


}
