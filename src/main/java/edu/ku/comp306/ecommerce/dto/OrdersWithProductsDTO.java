package edu.ku.comp306.ecommerce.dto;

import edu.ku.comp306.ecommerce.entity.OrderContains;
import edu.ku.comp306.ecommerce.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class OrdersWithProductsDTO {
    private Orders order;
    private List<OrderContains> products;
}