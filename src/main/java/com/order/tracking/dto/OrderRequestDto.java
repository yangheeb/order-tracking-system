package com.order.tracking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

import com.order.tracking.entity.OrderStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {
    @NotBlank(message = "이름은 필수 입력값입니다")
    private String name;

    @NotBlank(message = "이메일은 필수 입력값입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank(message = "전화번호는 필수 입력값입니다")
    @Size(min = 10, max = 20, message = "전화번호는 10-20자 사이여야 합니다")
    private String phone;

    @NotBlank(message = "우편번호는 필수 입력값입니다")
    private String zipCode;

    @NotBlank(message = "기본주소는 필수 입력값입니다")
    private String baseAddress;

    private String detailAddress;
    private String extraAddress;

    @NotNull(message = "주문 상품은 필수 입력값입니다")
    @Size(min = 1, message = "최소 1개 이상의 상품을 주문해야 합니다")
    private List<OrderItemRequestDto> items;

    private String orderNumber;
    private BigDecimal totalAmount;
    private OrderStatus status;

    @Data
    public static class OrderItemRequestDto {
        @NotNull(message = "상품 ID는 필수 입력값입니다")
        private Long productId;

        @NotNull(message = "수량은 필수 입력값입니다")
        private Integer quantity;
    }
} 