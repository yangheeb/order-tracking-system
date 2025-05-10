package com.order.tracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

// OrderItem entity is referenced by Order (OneToMany) and Product (ManyToOne).
// It is used to represent order details (individual items in an order) in the system.
// This entity is essential for order processing and is used in service and controller layers.
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal price;

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    // 주문 아이템 생성 메서드
    public static OrderItem createOrderItem(Product product, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice());
        return orderItem;
    }

    // 주문 아이템 총 가격 계산
    public Long getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity)).longValue();
    }
} 