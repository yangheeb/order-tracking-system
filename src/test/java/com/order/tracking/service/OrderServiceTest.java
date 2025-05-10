package com.order.tracking.service;

import com.order.tracking.entity.Customer;
import com.order.tracking.entity.Order;
import com.order.tracking.entity.OrderStatus;
import com.order.tracking.exception.ResourceNotFoundException;
import com.order.tracking.mapper.OrderMapper;
import com.order.tracking.repository.OrderRepository;
import com.order.tracking.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .name("Test Customer")
                .email("test@example.com")
                .phone("123-456-7890")
                .build();

        order = Order.builder()
                .id(1L)
                .orderNumber("TEST-001")
                .customer(customer)
                .status(OrderStatus.PENDING)
                .totalAmount(new BigDecimal("100.00"))
                .build();
    }

    @Nested
    @DisplayName("createOrder 메서드는")
    class Describe_createOrder {

        @Test
        @DisplayName("주문을 생성하고 반환한다")
        void createOrder_ShouldReturnCreatedOrder() {
            // given
            when(orderRepository.save(any(Order.class))).thenReturn(order);

            // when
            Order result = orderService.createOrder(order);

            // then
            assertNotNull(result);
            assertEquals("TEST-001", result.getOrderNumber());
            assertEquals(OrderStatus.PENDING, result.getStatus());
            verify(orderRepository).save(any(Order.class));
        }
    }

    @Nested
    @DisplayName("getOrder 메서드는")
    class Describe_getOrder {

        @Test
        @DisplayName("주문 ID로 주문을 조회한다")
        void getOrder_ShouldReturnOrder_WhenOrderExists() {
            // given
            when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

            // when
            Order result = orderService.getOrder(1L);

            // then
            assertNotNull(result);
            assertEquals("TEST-001", result.getOrderNumber());
            verify(orderRepository).findById(1L);
        }

        @Test
        @DisplayName("존재하지 않는 주문 ID로 조회 시 예외를 발생시킨다")
        void getOrder_ShouldThrowException_WhenOrderDoesNotExist() {
            // given
            when(orderRepository.findById(999L)).thenReturn(Optional.empty());

            // when & then
            assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(999L));
        }
    }

    @Nested
    @DisplayName("updateOrderStatus 메서드는")
    class Describe_updateOrderStatus {

        @Test
        @DisplayName("주문 상태를 업데이트한다")
        void updateOrderStatus_ShouldUpdateStatus() {
            // given
            when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
            when(orderRepository.save(any(Order.class))).thenReturn(order);

            // when
            Order result = orderService.updateOrderStatus(1L, OrderStatus.PROCESSING);

            // then
            assertNotNull(result);
            assertEquals(OrderStatus.PROCESSING, result.getStatus());
            verify(orderRepository).save(any(Order.class));
        }
    }

    @Nested
    @DisplayName("deleteOrder 메서드는")
    class Describe_deleteOrder {

        @Test
        @DisplayName("주문을 삭제한다")
        void deleteOrder_ShouldDeleteOrder() {
            // given
            when(orderRepository.existsById(1L)).thenReturn(true);

            // when
            orderService.deleteOrder(1L);

            // then
            verify(orderRepository).deleteById(1L);
        }
    }
} 