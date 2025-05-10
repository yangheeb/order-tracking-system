package com.order.tracking.mapper;

import com.order.tracking.dto.OrderRequestDto;
import com.order.tracking.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    
    public Order toEntity(OrderRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Order.builder()
                .orderNumber(dto.getOrderNumber())
                .totalAmount(dto.getTotalAmount())
                .status(dto.getStatus())
                .build();
    }

    public OrderRequestDto toDto(Order entity) {
        if (entity == null) {
            return null;
        }

        return OrderRequestDto.builder()
                .orderNumber(entity.getOrderNumber())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus())
                .build();
    }
} 