package com.order.tracking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.tracking.entity.Order;
import com.order.tracking.entity.OrderStatus;
import com.order.tracking.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Order order;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();

        order = Order.builder()
                .id(1L)
                .orderNumber("TEST-001")
                .status(OrderStatus.PENDING)
                .totalAmount(new BigDecimal("100.00"))
                .build();
    }

    @Nested
    @DisplayName("createOrder 메서드는")
    class Describe_createOrder {

        @Test
        @DisplayName("주문을 생성하고 반환한다")
        void createOrder_ShouldReturnCreatedOrder() throws Exception {
            // given
            when(orderService.createOrder(any(Order.class))).thenReturn(order);

            // when & then
            mockMvc.perform(post("/api/v1/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(order)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderNumber").value("TEST-001"))
                    .andExpect(jsonPath("$.status").value("PENDING"))
                    .andExpect(jsonPath("$.totalAmount").value(100.00));
        }
    }

    @Nested
    @DisplayName("getOrder 메서드는")
    class Describe_getOrder {

        @Test
        @DisplayName("주문 ID로 주문을 조회한다")
        void getOrder_ShouldReturnOrder_WhenOrderExists() throws Exception {
            // given
            when(orderService.getOrder(anyLong())).thenReturn(order);

            // when & then
            mockMvc.perform(get("/api/v1/orders/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderNumber").value("TEST-001"))
                    .andExpect(jsonPath("$.status").value("PENDING"))
                    .andExpect(jsonPath("$.totalAmount").value(100.00));
        }
    }

    @Nested
    @DisplayName("updateOrderStatus 메서드는")
    class Describe_updateOrderStatus {

        @Test
        @DisplayName("주문 상태를 업데이트한다")
        void updateOrderStatus_ShouldUpdateStatus() throws Exception {
            // given
            Order updatedOrder = Order.builder()
                    .id(1L)
                    .orderNumber("TEST-001")
                    .status(OrderStatus.SHIPPED)
                    .totalAmount(new BigDecimal("100.00"))
                    .build();

            when(orderService.updateOrderStatus(anyLong(), any(OrderStatus.class))).thenReturn(updatedOrder);

            // when & then
            mockMvc.perform(patch("/api/v1/orders/1/status")
                    .param("status", "SHIPPED"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.orderNumber").value("TEST-001"))
                    .andExpect(jsonPath("$.status").value("SHIPPED"))
                    .andExpect(jsonPath("$.totalAmount").value(100.00));
        }
    }

    @Nested
    @DisplayName("deleteOrder 메서드는")
    class Describe_deleteOrder {

        @Test
        @DisplayName("주문을 삭제한다")
        void deleteOrder_ShouldDeleteOrder() throws Exception {
            // when & then
            mockMvc.perform(delete("/api/v1/orders/1"))
                    .andExpect(status().isNoContent());
        }
    }
} 