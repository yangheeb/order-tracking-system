package com.order.tracking.service;

public interface EmailService {
    void sendOrderStatusUpdate(String to, String subject, String content);
    void sendOrderConfirmation(String to, String subject, String content);
    void sendOrderCancellation(String to, String subject, String content);
} 