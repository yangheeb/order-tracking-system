package com.order.tracking.dto;

import com.order.tracking.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String shippingPhone;
    private String shippingName;
    private OrderStatus status;
    private String trackingNumber;
    private List<OrderItemResponseDto> items;
    private Long totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class OrderItemResponseDto {
        private Long id;
        private String productName;
        private Integer quantity;
        private Long price;
        private Long totalPrice;
    }
} 