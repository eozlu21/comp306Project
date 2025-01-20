package edu.ku.comp306.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderId;
    private List<OrderItemDto> orderItems;
    private LocalDate orderDate;

    public int getNumberOfItems(){
        return orderItems.stream().mapToInt(OrderItemDto::getQuantity).sum();
    }

    public double getTotalPrice(){
        return orderItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
    }
}
