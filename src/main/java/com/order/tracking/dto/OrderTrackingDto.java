package com.order.tracking.dto;

import com.order.tracking.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderTrackingDto {
    private Long orderId;
    private String orderNumber;
    private OrderStatus currentStatus;
    private String trackingNumber;
    private List<OrderStatusHistoryDto> history;

    @Data
    public static class OrderStatusHistoryDto {
        private OrderStatus status;
        private String reason;
        private LocalDateTime timestamp;
    }
} 