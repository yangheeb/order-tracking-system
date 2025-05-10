package com.order.tracking.dto;

import com.order.tracking.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusUpdateDto {
    @NotNull(message = "주문 상태는 필수 입력값입니다")
    private OrderStatus status;

    private String reason;
} 