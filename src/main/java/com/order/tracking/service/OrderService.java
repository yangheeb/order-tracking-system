package com.order.tracking.service;

import com.order.tracking.entity.Order;
import com.order.tracking.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    Order getOrder(Long id);
    Order getOrderByOrderNumber(String orderNumber);
    List<Order> getOrdersByCustomerId(Long customerId);
    Order updateOrderStatus(Long id, OrderStatus status);
    void deleteOrder(Long id);
} 