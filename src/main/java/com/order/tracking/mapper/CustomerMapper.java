package com.order.tracking.mapper;

import com.order.tracking.dto.CustomerRequestDto;
import com.order.tracking.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    
    public Customer toEntity(CustomerRequestDto dto) {
        if (dto == null) {
            return null;
        }

        return Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
    }

    public CustomerRequestDto toDto(Customer entity) {
        if (entity == null) {
            return null;
        }

        return CustomerRequestDto.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .build();
    }
} 