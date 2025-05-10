package com.order.tracking.consumer;

import com.order.tracking.config.RabbitMQConfig;
import com.order.tracking.entity.Order;
import com.order.tracking.entity.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStatusConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_STATUS_QUEUE)
    public void handleOrderStatusUpdate(Order order) {
        log.info("Received order status update for order: {}", order.getOrderNumber());
        
        // TODO: Implement email notification
        sendEmailNotification(order);
        
        // TODO: Implement SMS notification
        sendSMSNotification(order);
    }

    private void sendEmailNotification(Order order) {
        String subject = "Order Status Update";
        String message = String.format(
            "Dear %s,\n\nYour order %s status has been updated to %s.\n\nBest regards,\nOrder Tracking System",
            order.getCustomer().getName(),
            order.getOrderNumber(),
            order.getStatus().getDescription()
        );
        
        // TODO: Implement email sending logic
        log.info("Sending email notification: {}", message);
    }

    private void sendSMSNotification(Order order) {
        String message = String.format(
            "Order %s status updated to %s",
            order.getOrderNumber(),
            order.getStatus().getDescription()
        );
        
        // TODO: Implement SMS sending logic
        log.info("Sending SMS notification: {}", message);
    }
} 