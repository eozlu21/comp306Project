package edu.ku.comp306.ecommerce.service;

import edu.ku.comp306.ecommerce.dto.CartDTO;
import edu.ku.comp306.ecommerce.dto.OrderDTO;
import edu.ku.comp306.ecommerce.dto.OrderItemDto;
import edu.ku.comp306.ecommerce.entity.OrderContains;
import edu.ku.comp306.ecommerce.entity.Orders;
import edu.ku.comp306.ecommerce.entity.Product;
import edu.ku.comp306.ecommerce.repository.OrderContainsRepository;
import edu.ku.comp306.ecommerce.repository.OrdersRepository;
import edu.ku.comp306.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrdersRepository ordersRepository;
    private final OrderContainsRepository orderContainsRepository;
    private final ProductRepository productRepository;

    public boolean hasPurchased(Integer userId, Integer productId) {
        long orderCount = ordersRepository.getOrderedCount(productId, userId);
        return orderCount > 0;
    }

    public int createOrderId(int userId, LocalDate orderDate) {
        ordersRepository.insertOrder(userId, orderDate);
        return ordersRepository.getLastInsertId();
    }

    public void createOrderContains(Integer userId, Integer orderId, CartDTO cart) {
        // Map CartItemDTO to OrderItemDto
        var orderItems = cart.getCartItems().stream()
                .map(item -> new OrderItemDto(item.getProduct(), item.getQuantity()))
                .toList();

        orderItems.forEach(item -> orderContainsRepository.createOrderContains(orderId, item.getProduct().getProductId(), item.getQuantity()));
    }
    public Object getOrderDetails(Integer userId, Integer orderId) {
        // Retrieve the specific order for the given user and order ID
        Orders order = ordersRepository.findByUserId(userId).stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order not found for the given user and order ID"));

        // Retrieve all items in the order
        List<OrderContains> orderContainsList = orderContainsRepository.findByOrderId(orderId);

        // Map each OrderContains entry to an OrderItemDto
        List<OrderItemDto> orderItemDtoList = orderContainsList.stream()
                .map(orderContains -> {
                    // Fetch the product information using the product ID
                    Product product = productRepository.findById(orderContains.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("Product not found"));

                    // Return a new instance of OrderItemDto with product and quantity
                    return new OrderItemDto(product, orderContains.getQuantity());
                })
                .toList();

        // Create and return the OrderDTO
        return new OrderDTO(orderId, orderItemDtoList, order.getOrderDate());
    }
}
